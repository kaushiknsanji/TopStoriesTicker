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
 * @author Kaushik N Sanji
 */
class MainViewModel(
    networkHelper: NetworkHelper,
    private val newsRepository: NewsRepository
) : BaseViewModel(networkHelper) {

    // LiveData for the complete data loading progress indication
    private val loadingProgress: MutableLiveData<Boolean> = MutableLiveData()

    // LiveData for each article loading progress indication
    private val _articleLoadingProgress: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val articleLoadingProgress: LiveData<Event<Boolean>> = _articleLoadingProgress

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
        loadingProgress.postValue(false)
        _articleLoadingProgress.postValue(Event(false))
    }

    init {
        // Start downloading the News Articles when initialized
        fetchEditorsPicks()
    }

    /**
     * Callback method to be implemented, which will be called when this ViewModel's Activity/Fragment is created.
     */
    override fun onCreate() {
        // Reload the list of articles downloaded till rotation
        _liveArticles.postValue(Event(loadedArticlesList))
    }

    /**
     * Called when the user swipes down to refresh the list.
     * Articles are downloaded again when [loadingProgress] is not active.
     */
    fun onRefresh() {
        // Checking for the ongoing progress if any
        loadingProgress.value?.takeIf { !it }.let {
            // When the last download had completed

            // Clear the current list and publish to adapter
            loadedArticlesList.clear()
            _liveArticles.postValue(Event(loadedArticlesList))

            // Start the download of News Articles again
            fetchEditorsPicks()
        }
    }

    /**
     * Downloads the list of [NewsArticle]s from the Remote API via the [NewsRepository]
     * using Coroutine and Coroutine-Flow.
     */
    private fun fetchEditorsPicks() = viewModelScope.launch(networkExceptionHandler) {

        // Get the list of News Articles from the Remote API
        newsRepository.doFetchEditorsPicks(
            DateUtility.getDateStringForXDaysAgo(X_DAYS_AGO),
            NEWS_TICKER_DELAY
        )
            .flowOn(Dispatchers.IO) // Execute Flow on the IO Dispatcher Context
            .onStart {
                // During start of each emission

                // Start the loading indication
                loadingProgress.postValue(true)
                _articleLoadingProgress.postValue(Event(true))
            }
            .onCompletion { cause: Throwable? ->
                // On Completion of the Flow

                if (cause == null) {
                    // When there is no Error, then the Flow has completed successfully.
                    // Hence stop the loading indication
                    loadingProgress.postValue(false)
                    _articleLoadingProgress.postValue(Event(false))
                } else {
                    // When there is an error, show and log the error
                    onError(cause)
                }
            }
            .catch { cause: Throwable ->
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

    }

    /**
     * Called when the user clicks on the News Card Item shown in the RecyclerView of [MainActivity].
     *
     * Triggers an event to launch the News Article in a System Browser.
     */
    fun onItemClick(article: NewsArticle) {
        _launchArticle.postValue(Event(article))
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