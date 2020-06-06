package com.kaushiknsanji.topstoriesticker.data.repository

import com.kaushiknsanji.topstoriesticker.data.remote.NewsService
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

}