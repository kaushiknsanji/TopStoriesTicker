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

import android.view.View

/**
 * Kotlin file for extension functions on android `View`.
 *
 * @author Kaushik N Sanji
 */

/**
 * Extension function on [View] to check if the [View] is hidden.
 * @return `true` if the [View] is hidden; `false` otherwise.
 */
fun View.isHidden() = visibility == View.GONE

/**
 * Extension function on [View] to make the [View] visible.
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Extension function on [View] to hide the [View].
 */
fun View.hide() {
    visibility = View.GONE
}
