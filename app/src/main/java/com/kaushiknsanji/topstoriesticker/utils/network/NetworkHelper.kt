package com.kaushiknsanji.topstoriesticker.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.kaushiknsanji.topstoriesticker.utils.log.Logger
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

/**
 * Utility class for Network related tasks.
 *
 * @property context The Application [Context] instance
 * @constructor Creates an Instance of [NetworkHelper]
 *
 * @author Kaushik N Sanji
 */
class NetworkHelper(private val context: Context) {

    companion object {
        // Constant used for logs
        private const val TAG = "NetworkHelper"
    }

    // Get the System Connectivity Manager instance
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // LiveData that maintains the current connectivity status
    private val _connectedLiveStatus = MutableLiveData(false)

    // Callback for receiving events when any network's connectivity changes
    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        // Maintain a list of active networks
        private val activeNetworks: MutableList<Network> = mutableListOf()

        /**
         * Called when the framework has a hard loss of the network or when the
         * graceful failure ends.
         *
         * @param network The [Network] lost.
         */
        override fun onLost(network: Network) {
            super.onLost(network)
            // On Loss of a network, remove the corresponding network if any from the
            // managed list of active networks
            activeNetworks.removeAll { activeNetwork -> activeNetwork == network }
            // Update the connectivity status based on available active networks
            updateConnectivityStatus(activeNetworks.isNotEmpty())
        }

        /**
         * Called when the framework connects and has declared a new network ready for use.
         * This callback may be called more than once if the [Network] that is
         * satisfying the request changes. This will always immediately be followed by a
         * call to [.onCapabilitiesChanged] then by a
         * call to [.onLinkPropertiesChanged], and a call to
         * [.onBlockedStatusChanged].
         *
         * @param network The [Network] of the satisfying network.
         */
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            // When a network is available, add to the managed list of active networks
            // if not previously added
            if (activeNetworks.none { activeNetwork -> activeNetwork == network }) {
                activeNetworks.add(network)
            }
            // Update the connectivity status based on available active networks
            updateConnectivityStatus(activeNetworks.isNotEmpty())
        }
    }

    /**
     * Updates the [_connectedLiveStatus] that saves the current state of any active Network connection
     *
     * @param status If `true` then there are some active networks available; `false` otherwise
     */
    private fun updateConnectivityStatus(status: Boolean) {
        _connectedLiveStatus.postValue(status)
    }

    init {
        // Try unregistering any previous registered callbacks
        try {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        } catch (e: Exception) {
            Logger.w(
                TAG,
                "NetworkCallback for networking was not registered or already unregistered"
            )
        }
        // Register a new callback on application start for all networks
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            connectivityCallback
        )
    }

    /**
     * Returns `true` if the Network is established; `false` otherwise
     */
    fun isNetworkConnected(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // For version M and above

        // Read status from the callback registered
        _connectedLiveStatus.value ?: false
    } else {
        // For version below M

        // Get the current active default data network in old style
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        // Check the connectivity status and return its state
        activeNetworkInfo?.isConnected ?: false
    }

    /**
     * Generates a [NetworkError] instance appropriately based on the [throwable]
     */
    fun castToNetworkError(throwable: Throwable): NetworkError {
        // Get the default NetworkError Instance
        val defaultNetworkError = NetworkError()
        try {
            // Returning appropriate NetworkError based on throwable
            return when (throwable) {
                // For throwable of ConnectException, return a NetworkError Instance with 0 status
                is ConnectException -> NetworkError(status = 0, statusCode = "0")
                // For throwable of Exception other than HttpException, return the default NetworkError Instance
                !is HttpException -> defaultNetworkError
                // For throwable of HttpException, prepare and return the NetworkError Instance from throwable errorBody
                else -> GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .fromJson(throwable.response().errorBody()?.string(), NetworkError::class.java)
                    .copy(status = throwable.code())
            }
        } catch (e: IOException) {
            Logger.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Logger.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Logger.e(TAG, e.toString())
        }
        // When throwable is invalid or in other unknown cases, return the default NetworkError Instance
        return defaultNetworkError
    }

}