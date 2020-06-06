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