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

package com.kaushiknsanji.topstoriesticker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.data.repository.NewsRepository
import com.kaushiknsanji.topstoriesticker.ui.base.BaseViewModel
import com.kaushiknsanji.topstoriesticker.utils.common.DateUtility
import com.kaushiknsanji.topstoriesticker.utils.common.Event
import com.kaushiknsanji.topstoriesticker.utils.log.Logger
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * [BaseViewModel] subclass for [MainActivity].
 *
 * @param networkHelper Instance of [NetworkHelper] provided by Dagger, to check
 * network connectivity status and handle network related issues.
 * @property newsRepository Instance of [NewsRepository] provided by Dagger, for the News
 * from Guardian News API.
 * @constructor Instance of [MainViewModel] created and provided by Dagger.
 *
 * @author Kaushik N Sanji
 */
class MainViewModel(
    networkHelper: NetworkHelper,
    private val newsRepository: NewsRepository
) : BaseViewModel(networkHelper) {

    // LiveData for downloading progress indication
    private val _loadingProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingProgress: LiveData<Boolean> = _loadingProgress

    // LiveData for the list of Articles to be loaded/reloaded
    private val _liveArticles: MutableLiveData<Event<List<NewsArticle>>> = MutableLiveData()
    val liveArticles: LiveData<Event<List<NewsArticle>>> =
        Transformations.map(_liveArticles) { event ->
            event.getContentIfNotConsumed()?.let { articles ->
                Event(articles.map { it }) // Publishing a copy so that RecyclerView diff's the changes
            }
        }

    // Local list of Articles downloaded
    private val loadedArticlesList: MutableList<NewsArticle> = mutableListOf()

    // Scroll Event LiveData when the RecyclerView needs to be scrolled to the Top
    private val _scrollToTop: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val scrollToTop: LiveData<Event<Boolean>> = _scrollToTop

    // LiveData for Article launch events
    private val _launchArticle: MutableLiveData<Event<NewsArticle>> = MutableLiveData()
    val launchArticle: LiveData<Event<NewsArticle>> = _launchArticle

    // LiveData for saving and restoring the last scrolled position on the RecyclerView
    private val _lastScrolledPosition: MutableLiveData<Event<Int>> = MutableLiveData()
    val lastScrolledPosition: LiveData<Event<Int>> = _lastScrolledPosition

    // Coroutine Exception Handler
    private val networkExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Show and log the error
        onError(throwable)
    }

    /**
     * Called when there is an error in the Coroutine process used for downloading the articles.
     *
     * @param throwable [Throwable] instance of the error thrown
     */
    private fun onError(throwable: Throwable?) {
        // Log the error
        Logger.e(TAG, "${throwable?.printStackTrace()}")
        // Delegate to handle any network related errors and display the same
        handleNetworkError(throwable)
        // Stop the loading indication on error
        _loadingProgress.postValue(false)
    }

    init {
        // Start downloading the News Articles when initialized
        fetchEditorsPicks()
    }

    /**
     * Callback method to be implemented, which will be called when this ViewModel's Activity/Fragment is created.
     */
    override fun onCreate() {
        // Reload the list of articles already downloaded (if any)
        _liveArticles.postValue(Event(loadedArticlesList))
    }

    /**
     * Called when the user swipes down to refresh the list.
     * Articles are downloaded again when [_loadingProgress] is not active.
     */
    fun onRefresh() {
        // Checking if there is no ongoing progress
        _loadingProgress.value?.takeIf { !it }.let {
            // When the last download had completed

            // Start the download of News Articles again
            fetchEditorsPicks()
        }
    }

    /**
     * Downloads the list of [NewsArticle]s from the Remote API via the [NewsRepository]
     * using Coroutine and Coroutine-Flow.
     */
    private fun fetchEditorsPicks() = viewModelScope.launch(networkExceptionHandler) {

        // Proceed when we have network connectivity, else show an error message
        if (checkInternetConnectionWithMessage()) {
            // Before starting, clear the current list and publish to adapter
            loadedArticlesList.clear()
            _liveArticles.postValue(Event(loadedArticlesList))

            // Get the list of News Articles from the Remote API
            newsRepository.doFetchEditorsPicks(
                DateUtility.getDateStringForXDaysAgo(X_DAYS_AGO),
                NEWS_TICKER_DELAY
            )
                .flowOn(Dispatchers.IO) // Context switched to IO for preceding operations
                .onStart {
                    // On Start of the Flow

                    // Start the loading indication
                    _loadingProgress.postValue(true)
                }
                .onCompletion { cause: Throwable? ->
                    // On Completion of the Flow

                    if (cause == null) {
                        // When there is no Error, then the Flow has completed successfully.
                        // Hence stop the loading indication
                        _loadingProgress.postValue(false)
                    } else {
                        // When there is an error, show and log the error
                        onError(cause)
                    }
                }
                .catch { cause: Throwable -> // Catch exceptions thrown by preceding operations
                    // When there is an error, show and log the error
                    onError(cause)
                }
                .collect { article: NewsArticle ->
                    // Load the New Article at the top of the list
                    loadedArticlesList.add(0, article)
                    // Publish to the adapter
                    _liveArticles.postValue(Event(loadedArticlesList)).also {
                        // Scroll to top after publish
                        _scrollToTop.postValue(Event(true))
                    }
                }
        } else {
            // When there is no connection, ensure the loading indication is stopped
            // (Note: Loading indication also starts on user initiated swipe-to-refresh)
            _loadingProgress.postValue(false)
        }

    }

    /**
     * Called when the user clicks on the News Card Item shown in the RecyclerView of [MainActivity].
     *
     * Triggers an event to launch the News Article in a System Browser.
     */
    fun onItemClick(article: NewsArticle) {
        _launchArticle.postValue(Event(article))
    }

    /**
     * Called when there is a change in the configuration of the App, like on screen rotation,
     * in order to save and restore the scrolled position later.
     *
     * Triggers an event to restore the scrolled position if there is no ongoing progress.
     */
    fun saveLastScrolledPosition(firstVisibleItemPosition: Int) {
        // Checking if there is no ongoing progress
        _loadingProgress.value?.takeIf { !it }.let {
            // When the last download had already completed

            // Trigger an event to restore the scrolled position
            _lastScrolledPosition.postValue(Event(firstVisibleItemPosition))
        }
    }

    companion object {
        // Constant for logs
        const val TAG = "MainViewModel"

        // Constant that signifies how often to publish a News Article to the client
        // Set to 2 seconds
        const val NEWS_TICKER_DELAY = 2000L

        // Constant that determines the date from which the News articles will be loaded.
        const val X_DAYS_AGO = 7
    }
}