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

/**
 * Singleton Object for configuring the Retrofit and providing the instance of
 * the required API Service.
 *
 * @author Kaushik N Sanji
 */
object Networking {

    // Timeout constant (10 seconds)
    private const val NETWORK_CALL_TIMEOUT = 10L

    /**
     * Factory method that configures the [Retrofit] and
     * provides an instance of the API for the [service] given.
     *
     * @param apiKey String containing the API Key
     * @param baseUrl String containing the Base URL of the API
     * @param cacheDir [File] location in the disk to be used for reading and writing cached responses
     * @param cacheSize [Long] value representing the Max size of the Cache in bytes
     *
     * @return Instance of [service] API
     */
    /*fun <T : Any> createService(
        apiKey: String,
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long,
        service: KClass<T>
    ): T = Retrofit.Builder() // Creating an Instance of Retrofit
        .baseUrl(baseUrl) //URL on which every endpoint will be appended
        .client(
            // Setting up HTTP Client using OkHttpClient
            OkHttpClient.Builder()
                // Configure Cache
                .cache(Cache(cacheDir, cacheSize))
                // Add Interceptor to append the API Key on the request
                .addInterceptor { chain: Interceptor.Chain ->
                    // Proceed with the New Request built to append the API Key
                    chain.proceed(
                        // Rebuilding a New Request from the original with the new URL
                        chain.request().newBuilder()
                            .url(
                                // Rebuilding the URL with the API Key appended
                                chain.request().url().newBuilder()
                                    .addQueryParameter(QueryArgs.API_KEY, getApiKey(apiKey))
                                    .build()
                            ).build()
                    )
                }
                // Add Interceptor to log the request and response
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            // Configure Logging with BODY level info only for DEBUG Build
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            // No Logging for any other Build
                            else HttpLoggingInterceptor.Level.NONE
                        })
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
        .create(service::class.java) as T*/

    /**
     * Factory method that configures the [Retrofit] and
     * provides an instance of the [NewsService] API.
     *
     * @param apiKey String containing the API Key
     * @param baseUrl String containing the Base URL of the API
     * @param cacheDir [File] location in the disk to be used for reading and writing cached responses
     * @param cacheSize [Long] value representing the Max size of the Cache in bytes
     *
     * @return Instance of [NewsService] API.
     */
    fun createService(
        apiKey: String,
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long
    ): NewsService = Retrofit.Builder() // Creating an Instance of Retrofit
        .baseUrl(baseUrl) //URL on which every endpoint will be appended
        .client(
            // Setting up HTTP Client using OkHttpClient
            OkHttpClient.Builder()
                // Configure Cache
                .cache(Cache(cacheDir, cacheSize))
                // Add Interceptor to append the API Key on the request
                .addInterceptor { chain: Interceptor.Chain ->
                    // Proceed with the New Request built to append the API Key
                    chain.proceed(
                        // Rebuilding a New Request from the original with the new URL
                        chain.request().newBuilder()
                            .url(
                                // Rebuilding the URL with the API Key appended
                                chain.request().url().newBuilder()
                                    .addQueryParameter(QueryArgs.API_KEY, getApiKey(apiKey))
                                    .build()
                            ).build()
                    )
                }
                // Add Interceptor to log the request and response
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            // Configure Logging with BODY level info only for DEBUG Build
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            // No Logging for any other Build
                            else HttpLoggingInterceptor.Level.NONE
                        })
                // Read Timeout for response
                .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                // Write Timeout for request
                .writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .build() // Generate the OkHttpClient instance
        )
        // GSON Converter Factory to parse JSON and construct Objects
        .addConverterFactory(GsonConverterFactory.create())
        .build() // Generate the Retrofit instance
        // Create the NewsService API with the Retrofit Configuration
        .create(NewsService::class.java)

    /**
     * Returns the default [QueryArgs.API_KEY_VAL_TEST] key to be used
     * if the provided [apiKey] is empty.
     */
    private fun getApiKey(apiKey: String) = if (apiKey.isEmpty()) {
        QueryArgs.API_KEY_VAL_TEST
    } else {
        apiKey
    }

}