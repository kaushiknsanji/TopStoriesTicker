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

/**
 * Singleton object for managing all the API Query Arguments in one place.
 *
 * @author Kaushik N Sanji
 */
object QueryArgs {

    // Argument for Editor's Picks
    const val SHOW_EDITOR_PICKS = "show-editors-picks"

    // Argument for start date
    const val FROM_DATE = "from-date"

    // Argument for output fields filtering
    const val SHOW_FIELDS = "show-fields"

    // Constants that restrict the output to required fields in SHOW_FIELDS
    const val SHOW_FIELDS_FILTER_TRAIL_TEXT = "trailText"
    const val SHOW_FIELDS_FILTER_BY_LINE = "byline"
    const val SHOW_FIELDS_FILTER_THUMBNAIL = "thumbnail"

    // API Key arguments
    const val API_KEY = "api-key"
    const val API_KEY_VAL_TEST = "test"

}