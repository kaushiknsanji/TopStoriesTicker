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