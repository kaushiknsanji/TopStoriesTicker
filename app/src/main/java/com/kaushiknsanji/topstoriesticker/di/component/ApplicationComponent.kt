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