package com.kaushiknsanji.topstoriesticker.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushiknsanji.topstoriesticker.data.repository.NewsRepository
import com.kaushiknsanji.topstoriesticker.di.ActivityContext
import com.kaushiknsanji.topstoriesticker.ui.base.BaseActivity
import com.kaushiknsanji.topstoriesticker.ui.main.MainViewModel
import com.kaushiknsanji.topstoriesticker.ui.main.news.NewsAdapter
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import com.kaushiknsanji.topstoriesticker.utils.viewmodel.ViewModelProviderFactory
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

    /**
     * Provides instance of [MainViewModel]
     */
    @Provides
    fun provideMainViewModel(
        networkHelper: NetworkHelper,
        newsRepository: NewsRepository
    ): MainViewModel = ViewModelProvider(
        activity,
        ViewModelProviderFactory(MainViewModel::class) {
            // [creator] lambda that creates and returns the ViewModel instance
            MainViewModel(networkHelper, newsRepository)
        }
    )[MainViewModel::class.java]

    /**
     * Provides instance of [NewsAdapter]
     */
    @Provides
    fun provideNewsAdapter(): NewsAdapter =
        NewsAdapter(activity.lifecycle, (activity as? NewsAdapter.Listener))
}