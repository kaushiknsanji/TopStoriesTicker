package com.kaushiknsanji.topstoriesticker.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushiknsanji.topstoriesticker.di.ActivityContext
import com.kaushiknsanji.topstoriesticker.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

/**
 * Dagger Module for creating and exposing dependencies, tied to the Activity Lifecycle.
 *
 * @author Kaushik N Sanji
 */
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    /**
     * Provides instance of activity [Context]
     */
    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    /**
     * Provides instance of Vertical [LinearLayoutManager]
     */
    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

}