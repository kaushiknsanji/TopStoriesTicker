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

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.kaushiknsanji.topstoriesticker.R

/**
 * Utility object for customizing Android [Toast].
 *
 * @author Kaushik N Sanji
 */
object Toaster {

    /**
     * Method that displays a [Toast] for the given [text] message
     * with the Text in Black Color, overlaid over White Color Background
     */
    fun show(context: Context, text: CharSequence) {
        //Create a Toast with the text message passed
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        //Set the Background Color to ColorPrimary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            toast.view.background.colorFilter =
                BlendModeColorFilter(R.color.colorPrimary, BlendMode.SRC_IN)
        } else {
            toast.view.background.setColorFilter(
                ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN
            )
        }
        //Get the TextView of Toast
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        //Set the Text Color to White
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        //Show the Toast
        toast.show()
    }

}