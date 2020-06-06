package com.kaushiknsanji.topstoriesticker.di.component

import android.app.Application
import android.content.Context
import com.kaushiknsanji.topstoriesticker.data.remote.NewsService
import com.kaushiknsanji.topstoriesticker.data.repository.NewsRepository
import com.kaushiknsanji.topstoriesticker.di.ApplicationContext
import com.kaushiknsanji.topstoriesticker.di.module.ApplicationModule
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger Component for exposing dependencies from the Module [ApplicationModule].
 *
 * @author Kaushik N Sanji
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    /**
     * Exposes [Application] instance
     */
    fun getApplication(): Application

    /**
     * Exposes Application [Context] instance
     */
    @ApplicationContext
    fun getContext(): Context

    /**
     * Exposes [NetworkHelper] instance
     */
    fun getNetworkHelper(): NetworkHelper

    /**
     * Exposes [NewsService] instance
     */
    fun getNewsService(): NewsService

    /**
     * Exposes [NewsRepository] instance created using constructor injection
     */
    fun getNewsRepository(): NewsRepository

}