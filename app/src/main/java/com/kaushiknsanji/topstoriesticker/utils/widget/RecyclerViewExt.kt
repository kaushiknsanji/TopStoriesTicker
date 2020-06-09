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

package com.kaushiknsanji.topstoriesticker.utils.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Kotlin file for extension functions on `RecyclerView`.
 *
 * @author Kaushik N Sanji
 */

/**
 * Extension function on [RecyclerView] that retrieves the item position of the
 * first completely visible or the partially visible item in its screen.
 *
 * @return [Int] value of the first item position that is currently visible in the screen.
 */
fun RecyclerView.getFirstVisibleItemPosition(): Int {
    // Retrieve the Layout Manager of the RecyclerView
    val layoutManager = layoutManager
    if (layoutManager is LinearLayoutManager) {
        // When the Layout Manager is an instance of LinearLayoutManager

        // Retrieve the top completely visible item position
        val position =
            layoutManager.findFirstCompletelyVisibleItemPosition()

        return if (position > RecyclerView.NO_POSITION) {
            // Returning the top completely visible item position if valid
            position
        } else {
            // Else, returning the top partially visible item position
            layoutManager.findFirstVisibleItemPosition()
        }

    } else if (layoutManager is StaggeredGridLayoutManager) {
        // When the Layout Manager is an instance of StaggeredGridLayoutManager

        // Retrieve the top completely visible item position
        val position = layoutManager.findFirstCompletelyVisibleItemPositions(null)[0]

        return if (position > RecyclerView.NO_POSITION) {
            // Returning the top completely visible item position if valid
            position
        } else {
            // Else, returning the top partially visible item position
            layoutManager.findFirstVisibleItemPositions(null)[0]
        }
    }

    // On all else, returning -1 (RecyclerView.NO_POSITION)
    return RecyclerView.NO_POSITION
}

/**
 * Extension function on [RecyclerView] that smoothly scrolls to the [targetPosition] in a
 * vertical [RecyclerView] such that the item gets displayed at the top of the parent [RecyclerView].
 */
fun RecyclerView.smoothVScrollToPositionWithViewTop(targetPosition: Int) {
    // Configure custom LinearSmoothScroller in order to align
    // the top of the child with the parent RecyclerView top always
    val linearSmoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
        /**
         * When scrolling towards a child view, this method defines whether we should align the top
         * or the bottom edge of the child with the parent RecyclerView.
         *
         * @return SNAP_TO_START, SNAP_TO_END or SNAP_TO_ANY; depending on the current target vector
         */
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }

    // Set the target position to scroll to
    linearSmoothScroller.targetPosition = targetPosition
    // Begin Smooth scroll with the scroller built
    layoutManager?.startSmoothScroll(linearSmoothScroller)
}