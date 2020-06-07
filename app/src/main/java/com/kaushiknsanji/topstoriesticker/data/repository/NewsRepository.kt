package com.kaushiknsanji.topstoriesticker.data.repository

import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.data.remote.NewsService
import com.kaushiknsanji.topstoriesticker.utils.common.DateUtility
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for News from the Guardian News API.
 *
 * @property newsService Instance of Retrofit API Service [NewsService] provided by Dagger.
 * @constructor Instance of [NewsRepository] created and provided by Dagger.
 *
 * @author Kaushik N Sanji
 */
@Singleton
class NewsRepository @Inject constructor(
    private val newsService: NewsService
) {


    /**
     * Performs "Editors Picks" request with the Remote API from the start date as [fromDate]
     * and returns a [kotlinx.coroutines.flow.Flow]
     * of [NewsArticle]s, each delayed by the specified [newsTickerDelay] (in ms) in order
     * to simulate News Ticker.
     */
    fun doFetchEditorsPicks(fromDate: String, newsTickerDelay: Long) = flow {
        newsService.doEditorPicksCall(fromDate = fromDate)
            .response
            .articles.toMutableList().apply {
                // Adding Dummy article for fun!
                add(
                    NewsArticle(
                        id = "111",
                        sectionId = "1112",
                        sectionName = "#30DaysOfKotlin",
                        publishedDate = DateUtility.getCurrentDateString(),
                        newsTitle = "Thank You Google!",
                        webUrl = "https://eventsonair.withgoogle.com/events/kotlin",
                        fields = NewsArticle.NewsField(
                            trailText = "Kotlin is Awesome!",
                            author = "Kaushik N. Sanji",
                            thumbnail = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
                        )
                    )
                )
            }
            .onEach { newsArticle: NewsArticle ->
                // Emit each news article one-by-one
                emit(newsArticle)
                // Pause in-between
                kotlinx.coroutines.delay(newsTickerDelay)
            }
    }


}