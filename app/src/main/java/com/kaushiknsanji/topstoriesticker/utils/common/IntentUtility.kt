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

package com.kaushiknsanji.topstoriesticker.utils.common

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Utility class that provides convenience methods for the common Intents
 * used in the app.
 *
 * @author Kaushik N Sanji
 */
object IntentUtility {

    /**
     * Method that opens a system Browser application to load the [webUrl] given.
     *
     * @param context [Context] of the calling Activity/Fragment
     */
    fun openLink(context: Context, webUrl: String) {
        // Parsing the URL
        val webPageUri = Uri.parse(webUrl)
        // Creating an ACTION_VIEW Intent with the URI
        val webIntent = Intent(Intent.ACTION_VIEW, webPageUri)
        // Checking if there is an Activity that accepts the Intent
        if (webIntent.resolveActivity(context.packageManager) != null) {
            // Launching the corresponding Activity and passing it the Intent
            context.startActivity(webIntent)
        }
    }

}