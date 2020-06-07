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

package com.kaushiknsanji.topstoriesticker

import android.app.Application
import com.kaushiknsanji.topstoriesticker.di.component.ApplicationComponent
import com.kaushiknsanji.topstoriesticker.di.component.DaggerApplicationComponent
import com.kaushiknsanji.topstoriesticker.di.module.ApplicationModule

/**
 * [Application] subclass for exposing [ApplicationComponent]
 */
class TopStoriesTickerApplication : Application() {

    // For Dagger ApplicationComponent
    lateinit var applicationComponent: ApplicationComponent

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     */
    override fun onCreate() {
        super.onCreate()

        // Build the Dagger ApplicationComponent
        applicationComponent = buildApplicationComponent()
    }

    /**
     * Builds and provides the Dagger [ApplicationComponent] instance.
     */
    private fun buildApplicationComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

}