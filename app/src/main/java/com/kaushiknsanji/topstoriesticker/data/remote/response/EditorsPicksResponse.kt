package com.kaushiknsanji.topstoriesticker.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle

/**
 * Response Data class for the Editors Picks API.
 */
data class EditorsPicksResponseContainer(
    @Expose
    @SerializedName("response")
    val response: EditorPicksResponse
) {
    /**
     * Embedded Data class of the Response.
     *
     * @author Kaushik N Sanji
     */
    data class EditorPicksResponse(
        @Expose
        @SerializedName("status")
        val status: String,

        @Expose
        @SerializedName("total")
        val totalArticleCount: Int,

        @Expose
        @SerializedName("pages")
        val pages: Int? = 1,

        @Expose
        @SerializedName("pageSize")
        val pageSize: Int? = 0,

        @Expose
        @SerializedName("editorsPicks")
        val articles: List<NewsArticle>
    )
}