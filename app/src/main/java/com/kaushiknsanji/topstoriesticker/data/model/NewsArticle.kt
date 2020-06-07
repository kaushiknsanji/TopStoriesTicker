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