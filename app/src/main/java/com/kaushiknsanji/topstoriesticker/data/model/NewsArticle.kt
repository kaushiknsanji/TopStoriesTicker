package com.kaushiknsanji.topstoriesticker.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
    ) {

        /**
         * Method that returns the Author of the News article
         *
         * @param fallback String containing the default message to be returned when NO Author is present
         * @return String containing the Author of the News article
         */
        fun getAuthor(fallback: String) = if (author.isNullOrEmpty()) {
            fallback
        } else {
            author
        }
    }

    companion object {
        // Constant for the DateTime format used in the Published Date of the News article
        const val ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    }

    /**
     * Method that returns the Published DateTime of the News Article
     * in the User's locale, in the sample format 'on Jan 14, 2018 at 1:50:00PM IST'
     *
     * @param fallback Default fallback string that will be returned when No date is available
     *                 or the date is not parseable
     * @return String containing the locale formatted Published DateTime of the News Article
     */
    fun getFormattedPublishedDate(fallback: String): String {
        if (publishedDate.isNullOrEmpty()) {
            // Returning with the fallback string when the Published date is not available
            return fallback
        }

        // Trimming the 'Z' in the DateTimeStamp if present: START
        val gmtDateTimeStr: String = if (publishedDate.endsWith("Z")) {
            publishedDate.substring(0, publishedDate.indexOf("Z"))
        } else {
            publishedDate
        }
        // Trimming the 'Z' in the DateTimeStamp if present: END

        // Parsing the Source DateTime which is in GMT: START
        val sdfParser = SimpleDateFormat(ISO_DATE_TIME_FORMAT, Locale.getDefault())
        sdfParser.timeZone = TimeZone.getTimeZone("GMT")
        var parsedDate: Date? = null
        try {
            parsedDate = sdfParser.parse(gmtDateTimeStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        // Parsing the Source DateTime which is in GMT: END

        // Returning with the fallback string if the DateTime Parsing failed
        if (parsedDate == null) {
            return fallback
        }

        // Formatting the Date and Time to User's locale
        val parsedDateStr =
            DateFormat.getDateInstance(DateFormat.MEDIUM).format(parsedDate)
        val parsedTimeStr =
            DateFormat.getTimeInstance(DateFormat.LONG).format(parsedDate)

        // Returning the formatted DateTime
        //(appearing in the sample format 'on Jan 14, 2018 at 1:50:00PM IST')
        return "on $parsedDateStr at $parsedTimeStr"
    }

}