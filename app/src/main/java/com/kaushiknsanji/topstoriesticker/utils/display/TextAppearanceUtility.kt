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

package com.kaushiknsanji.topstoriesticker.utils.display

import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder

/**
 * Utility object for Text Appearance related modifications with classes like [SpannableStringBuilder]
 *
 * @author Kaushik N Sanji
 */
object TextAppearanceUtility {

    /**
     * Method that prepares and returns the Html Formatted text for the Text String passed [textWithHtmlContent]
     *
     * @return String containing the Html formatted text of [textWithHtmlContent]
     */
    fun getHtmlFormattedText(textWithHtmlContent: String): String {
        // Initialize a SpannableStringBuilder to build the text
        val spannableStringBuilder = SpannableStringBuilder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // For Android N and above
            spannableStringBuilder.append(
                Html.fromHtml(
                    textWithHtmlContent,
                    Html.FROM_HTML_MODE_COMPACT
                )
            )
        } else {
            // For older versions
            spannableStringBuilder.append(Html.fromHtml(textWithHtmlContent))
        }

        // Return the Formatted Html Text
        return spannableStringBuilder.toString()
    }

}