/*
 * Copyright 2020 Kaushik N. Sanji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaushiknsanji.topstoriesticker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaushiknsanji.topstoriesticker.R
import com.kaushiknsanji.topstoriesticker.utils.common.Resource
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import javax.net.ssl.HttpsURLConnection

/**
 * An abstract base [ViewModel] for all the ViewModels in the app, that provides abstraction to common tasks.
 *
 * @property networkHelper Instance of [NetworkHelper] provided by Dagger, to check
 * network connectivity status and handle network related issues.
 *
 * @author Kaushik N Sanji
 */
abstract class BaseViewModel(
    protected val networkHelper: NetworkHelper
) : ViewModel() {

    // LiveData for the Messages that needs to be shown in Toast/Snackbar
    protected val _messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val messageString: LiveData<Resource<String>> = _messageString
    protected val _messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageStringId: LiveData<Resource<Int>> = _messageStringId

    /**
     * Method that checks the Internet connectivity.
     *
     * @return Returns `true` if connected; `false` otherwise.
     */
    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    /**
     * Method that checks the Internet connectivity. If not connected, then an error message
     * will be posted to [_messageStringId] LiveData.
     *
     * @return Returns `true` if connected; `false` otherwise.
     */
    protected fun checkInternetConnectionWithMessage(): Boolean =
        if (checkInternetConnection()) {
            true
        } else {
            // Post an error message when not connected
            _messageStringId.postValue(Resource.Error(R.string.error_network_connection_issue))
            false
        }

    /**
     * Method that checks for the [err] and posts an appropriate error message to [_messageStringId] LiveData.
     */
    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            // When we have an error, convert it to NetworkError and post error messages based on its status
            networkHelper.castToNetworkError(err).run {
                when (status) {
                    // For default error
                    -1 -> _messageStringId.postValue(Resource.Error(R.string.error_network_default_issue))
                    // For Connect exceptions
                    0 -> _messageStringId.postValue(Resource.Error(R.string.error_network_server_connection_issue))
                    // For HTTP 500 error
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        _messageStringId.postValue(Resource.Error(R.string.error_network_internal_issue))
                    // For HTTP 503 error
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        _messageStringId.postValue(Resource.Error(R.string.error_network_server_not_available_issue))
                    // For other errors
                    else -> _messageString.postValue(Resource.Error(message))
                }
            }
        }

    /**
     * Callback method to be implemented, which will be called when this ViewModel's Activity/Fragment is created.
     */
    abstract fun onCreate()
}