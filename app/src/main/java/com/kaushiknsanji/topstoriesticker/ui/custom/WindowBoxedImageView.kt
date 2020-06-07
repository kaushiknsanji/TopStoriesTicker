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

package com.kaushiknsanji.topstoriesticker.ui.custom

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

/**
 * Custom [AppCompatImageView] class that extends [AppCompatImageView.setFrame(int, int, int, int)]
 * to resize/scale the image/drawable to 70 percent zoom in portrait mode
 * and to 100 percent zoom in landscape mode. The images are cropped and centered in the View Frame.
 * As with 70 percent zoom, images cropped will have black bars. This is known as Window Boxing,
 * hence the name.
 *
 * @author Kaushik N Sanji
 */
class WindowBoxedImageView(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {

    companion object {
        // Constant for the Picture Zoom level in portrait mode
        private const val PORTRAIT_ZOOM_LEVEL = 0.70f
    }

    /**
     * Method extended to resize/scale the image/drawable to 70 percent zoom in portrait mode
     * and to 100 percent zoom in landscape mode (with occurrence of black bars in 70 percent zoom)
     *
     * @param frameLeft   is the Integer value of the position of the Left edge of the image frame
     * @param frameTop    is the Integer value of the position of the Top edge of the image frame
     * @param frameRight  is the Integer value of the position of the Right edge of the image frame
     * @param frameBottom is the Integer value of the position of the Bottom edge of the image frame
     * @return Boolean value. True if the new size and position are different than the previous ones
     */
    override fun setFrame(
        frameLeft: Int,
        frameTop: Int,
        frameRight: Int,
        frameBottom: Int
    ): Boolean {
        //Retrieving the current Drawable
        val imageDrawable = drawable
        if (imageDrawable != null) {
            //If Drawable is present

            //Retrieving the current screen orientation
            val screenOrientation = resources.configuration.orientation
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                //When the device is in portrait mode

                //Calculating the dimensions of the Frame
                val frameWidth = frameRight - frameLeft.toFloat()
                val frameHeight = frameBottom - frameTop.toFloat()

                //Retrieving the dimensions of the Drawable
                val imageDrawableWidth =
                    imageDrawable.intrinsicWidth.toFloat()
                val imageDrawableHeight =
                    imageDrawable.intrinsicHeight.toFloat()

                //Retrieving the current Image Matrix
                val imageMatrix = imageMatrix

                //Calculating the scale based on the image dimensions retaining its aspect ratio
                val scale =
                    if (imageDrawableWidth * frameHeight > frameWidth * imageDrawableHeight) {
                        frameHeight / imageDrawableHeight * PORTRAIT_ZOOM_LEVEL
                    } else {
                        frameWidth / imageDrawableWidth * PORTRAIT_ZOOM_LEVEL
                    }

                //Centering the Image in the View Frame based on the scaled dimensions
                val dx = (frameWidth - imageDrawableWidth * scale) * 0.5f
                val dy = (frameHeight - imageDrawableHeight * scale) * 0.5f

                //Applying a new Image Matrix with the scale calculated
                imageMatrix.setScale(scale, scale)
                //Translating the scaled image to the center of the frame
                imageMatrix.postTranslate(
                    kotlin.math.round(dx),
                    kotlin.math.round(dy)
                )

                //Set scale type to Matrix
                scaleType = ScaleType.MATRIX

                //Pass the modified Matrix as the Image Matrix to be used
                setImageMatrix(imageMatrix)
            } else {

                //Set scale type to Center Crop
                scaleType = ScaleType.CENTER_CROP
            }
        }

        //Calling to super and returning its value
        return super.setFrame(frameLeft, frameTop, frameRight, frameBottom)
    }

    /**
     * Sets a Bitmap as the content of this ImageView.
     *
     * @param bm The bitmap to set
     */
    override fun setImageBitmap(bm: Bitmap?) {
        //Applying a black background for the black bar appearance
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
        //Propagating the call to super class
        super.setImageBitmap(bm)
    }

    /**
     * Sets a drawable as the content of this ImageView.
     *
     * @param resId the resource identifier of the drawable
     */
    override fun setImageResource(resId: Int) {
        //Applying a black background for the black bar appearance
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
        //Propagating the call to super class
        super.setImageResource(resId)
    }

    /**
     * Sets a Drawable as the content of this ImageView.
     *
     * @param drawable The drawable to set
     */
    override fun setImageDrawable(drawable: Drawable?) {
        //Applying a black background for the black bar appearance
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
        //Propagating the call to super class
        super.setImageDrawable(drawable)
    }
}