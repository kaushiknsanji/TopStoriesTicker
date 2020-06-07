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

package com.kaushiknsanji.topstoriesticker.utils.log

import com.kaushiknsanji.topstoriesticker.BuildConfig
import timber.log.Timber

/**
 * [Logger] object for the entire app that wraps [Timber] logger.
 *
 * @author Kaushik N Sanji
 */
object Logger {

    init {

        // Initialize Logger/Timber only for the DEBUG BuildType
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun d(tag: String, message: String, vararg params: Any) =
        Timber.tag(tag).d(message, params)

    fun d(tag: String, throwable: Throwable, message: String, vararg params: Any) =
        Timber.tag(tag).d(throwable, message, params)

    fun i(tag: String, message: String, vararg params: Any) =
        Timber.tag(tag).i(message, params)

    fun i(tag: String, throwable: Throwable, message: String, vararg params: Any) =
        Timber.tag(tag).i(throwable, message, params)

    fun w(tag: String, message: String, vararg params: Any) =
        Timber.tag(tag).w(message, params)

    fun w(tag: String, throwable: Throwable, message: String, vararg params: Any) =
        Timber.tag(tag).w(throwable, message, params)

    fun e(tag: String, message: String, vararg params: Any) =
        Timber.tag(tag).e(message, params)

    fun e(tag: String, throwable: Throwable, message: String, vararg params: Any) =
        Timber.tag(tag).e(throwable, message, params)

}