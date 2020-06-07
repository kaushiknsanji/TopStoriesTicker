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