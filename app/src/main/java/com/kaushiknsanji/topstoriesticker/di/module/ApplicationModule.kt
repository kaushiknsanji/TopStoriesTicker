package com.kaushiknsanji.topstoriesticker.di.module

import android.app.Application
import android.content.Context
import com.kaushiknsanji.topstoriesticker.BuildConfig
import com.kaushiknsanji.topstoriesticker.TopStoriesTickerApplication
import com.kaushiknsanji.topstoriesticker.data.remote.Networking
import com.kaushiknsanji.topstoriesticker.data.remote.NewsService
import com.kaushiknsanji.topstoriesticker.di.ApplicationContext
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module for creating and exposing dependencies, tied to the Application Lifecycle.
 *
 * @author Kaushik N Sanji
 */
@Module
class ApplicationModule(private val application: TopStoriesTickerApplication) {

    /**
     * Provides [Singleton] instance of [TopStoriesTickerApplication]
     */
    @Singleton
    @Provides
    fun provideApplication(): Application = application

    /**
     * Provides [Singleton] instance of [application] context
     */
    @Singleton
    @ApplicationContext
    @Provides
    fun provideContext(): Context = application

    /**
     * Provides [Singleton] instance of [NetworkHelper]
     */
    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    /**
     * Provides [Singleton] instance of [NewsService]
     */
    @Singleton
    @Provides
    fun provideNewsService(): NewsService = Networking.createService(
        BuildConfig.GUARDIAN_API_KEY,
        BuildConfig.BASE_URL,
        application.cacheDir,
        10 * 1024 * 1024 // 10MB Cache Size
    )

}