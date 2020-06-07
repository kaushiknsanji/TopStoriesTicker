package com.kaushiknsanji.topstoriesticker.utils.common

import android.os.Build
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.Period
import java.util.*

/**
 * Utility class that provides convenience methods
 * for working with the [java.util.Date] and [Calendar].
 *
 * @author Kaushik N Sanji
 */
object DateUtility {

    // Constant for the DateTime format used in the Published Date of the News article
    private const val ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * Method that returns the Published DateTime of the News Article
     * in the User's locale, in the sample format 'on Jan 14, 2018 at 1:50:00PM IST'
     *
     * @param publishedDate [String] representation of Date present in the format of [ISO_DATE_TIME_FORMAT]
     * @param fallback Default fallback string that will be returned when No date is available
     *                 or the date is not parseable
     * @return String containing the locale formatted Published DateTime of the News Article
     */
    fun getFormattedPublishedDate(publishedDate: String?, fallback: String): String {
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
        //(appearing in the sample format 'on Jun 07, 2020 at 1:50:00PM IST')
        return "on $parsedDateStr at $parsedTimeStr"
    }

    /**
     * Prepares and returns the Current Days' Date - [noOfDaysAgo] in the format [ISO_DATE_TIME_FORMAT]
     */
    fun getDateStringForXDaysAgo(noOfDaysAgo: Int): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For versions from O, use the new Time library
            val now = Instant.now() // Retrieves the current datetime
            // Returns the current datetime - noOfDaysAgo in the ISO format for UTC
            now.minus(Period.ofDays(noOfDaysAgo)).toString()
        } else {
            // For older versions

            // Get the Calendar instance for GMT
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
            // Subtract by the given number of days
            calendar.add(Calendar.DATE, -noOfDaysAgo)
            // Get the formatter for ISO DATETIME
            val dateFormatter = SimpleDateFormat(ISO_DATE_TIME_FORMAT, Locale.getDefault())
            // Format and return the date
            dateFormatter.format(calendar.time)
        }

    /**
     * Returns the Current Date in [ISO_DATE_TIME_FORMAT]
     */
    fun getCurrentDateString(): String = getDateStringForXDaysAgo(0)

}