package com.kaushiknsanji.topstoriesticker.data.remote

import com.kaushiknsanji.topstoriesticker.data.remote.response.EditorsPicksResponseContainer
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API Interface for the Guardian News API.
 *
 * @author Kaushik N Sanji
 */
interface NewsService {

    /**
     * API method to get all the Editor Picked News Articles
     */
    @GET(Endpoints.INTERNATIONAL)
    suspend fun doEditorPicksCall(
        @Query(QueryArgs.SHOW_EDITOR_PICKS) show: String = true.toString(),
        @Query(QueryArgs.FROM_DATE) fromDate: String,
        @Query(QueryArgs.SHOW_FIELDS) fieldsFilter: String = listOf(
            QueryArgs.SHOW_FIELDS_FILTER_BY_LINE,
            QueryArgs.SHOW_FIELDS_FILTER_TRAIL_TEXT,
            QueryArgs.SHOW_FIELDS_FILTER_THUMBNAIL
        ).joinToString(separator = ",")
    ): EditorsPicksResponseContainer

}