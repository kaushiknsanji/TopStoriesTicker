package com.kaushiknsanji.topstoriesticker.data.remote

/**
 * Singleton object for managing all the API Query Arguments in one place.
 *
 * @author Kaushik N Sanji
 */
object QueryArgs {

    const val SHOW_EDITOR_PICKS = "show-editors-picks"

    const val FROM_DATE = "from-date"

    const val SHOW_FIELDS = "show-fields"

    // Constants that restrict the output to required fields in SHOW_FIELDS
    const val SHOW_FIELDS_FILTER_TRAIL_TEXT = "trailText"
    const val SHOW_FIELDS_FILTER_BY_LINE = "byline"
    const val SHOW_FIELDS_FILTER_THUMBNAIL = "thumbnail"

    // API Key arguments
    const val API_KEY = "api-key"
    const val API_KEY_VAL_TEST = "test"

}