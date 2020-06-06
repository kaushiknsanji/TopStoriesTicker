package com.kaushiknsanji.topstoriesticker.data.remote

import com.kaushiknsanji.topstoriesticker.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * Singleton Object for configuring the Retrofit and providing the instance of
 * the required API Service.
 *
 * @author Kaushik N Sanji
 */
object Networking {

    // Timeout constant (in seconds)
    private const val NETWORK_CALL_TIMEOUT = 60L

    /**
     * Factory method that configures the [Retrofit] and
     * provides an instance of the API for the [kClassService] given.
     *
     * @param apiKey String containing the API Key
     * @param baseUrl String containing the Base URL of the API
     * @param cacheDir [File] location in the disk to be used for reading and writing cached responses
     * @param cacheSize [Long] value representing the Max size of the Cache in bytes
     *
     * @return Instance of [kClassService] API
     */
    fun <T : Any> createService(
        apiKey: String,
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long,
        kClassService: KClass<T>
    ): KClass<T> = Retrofit.Builder() // Creating an Instance of Retrofit
        .baseUrl(baseUrl) //URL on which every endpoint will be appended
        .client(
            // Setting up HTTP Client using OkHttpClient
            OkHttpClient.Builder()
                // Configure Cache
                .cache(Cache(cacheDir, cacheSize))
                // Add Interceptor to log the request and response
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            // Configure Logging with BODY level info only for DEBUG Build
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            // No Logging for any other Build
                            else HttpLoggingInterceptor.Level.NONE
                        })
                // Add Interceptor to append the API Key on the request
                .addInterceptor { chain: Interceptor.Chain ->
                    // Proceed with the New Request built to append the API Key
                    chain.proceed(
                        // Rebuilding a New Request from the original with the new URL
                        chain.request().newBuilder()
                            .url(
                                // Rebuilding the URL with the API Key appended
                                chain.request().url().newBuilder()
                                    .addQueryParameter(QueryArgs.API_KEY, apiKey)
                                    .build()
                            ).build()
                    )
                }
                // Read Timeout for response
                .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                // Write Timeout for request
                .writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .build() // Generate the OkHttpClient instance
        )
        // GSON Converter Factory to parse JSON and construct Objects
        .addConverterFactory(GsonConverterFactory.create())
        .build() // Generate the Retrofit instance
        // Create the API for the Service with the Retrofit Configuration
        .create(kClassService::class.java)

}