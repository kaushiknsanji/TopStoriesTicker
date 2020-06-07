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