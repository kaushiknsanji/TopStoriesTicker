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

package com.kaushiknsanji.topstoriesticker.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Application-wide Data Model for News Articles, embedded in API Responses.
 */
data class NewsArticle(
    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("sectionId")
    val sectionId: String,

    @Expose
    @SerializedName("sectionName")
    val sectionName: String,

    @Expose
    @SerializedName("webPublicationDate")
    val publishedDate: String? = "",

    @Expose
    @SerializedName("webTitle")
    val newsTitle: String,

    @Expose
    @SerializedName("webUrl")
    val webUrl: String,

    @Expose
    @SerializedName("fields")
    val fields: NewsField
) {

    /**
     * Embedded Data class for extra fields information.
     *
     * @author Kaushik N Sanji
     */
    data class NewsField(
        @Expose
        @SerializedName("trailText")
        val trailText: String? = "",

        @Expose
        @SerializedName("byline")
        val author: String? = "",

        @Expose
        @SerializedName("thumbnail")
        val thumbnail: String? = ""
    )

}