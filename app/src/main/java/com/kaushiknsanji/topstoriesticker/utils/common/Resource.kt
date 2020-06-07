package com.kaushiknsanji.topstoriesticker.utils.common

/**
 * Sealed Class used as Statuses with content wrapped in [dataEvent].
 *
 * @param T the type of content wrapped in the [dataEvent] of this [Resource]
 * @property status [Status] metadata information
 * @property dataEvent Any content of type [T] wrapped in an [Event]. Can be `null`.
 * @constructor A Private Constructor that creates an Instance of [Resource]
 *
 * @author Kaushik N Sanji
 */
sealed class Resource<out T>(val status: Status, val dataEvent: Event<T>?) {

    /**
     * Data Class for [Status.SUCCESS] Status
     *
     * @property data The content to be wrapped in the [dataEvent] of the [Resource]
     * @constructor Creates a factory constructor for [Status.SUCCESS] Status
     */
    data class Success<out T>(private val data: T? = null) :
        Resource<T>(Status.SUCCESS, data?.let { Event(it) })

    /**
     * Data Class for [Status.ERROR] Status
     *
     * @property data The content to be wrapped in the [dataEvent] of the [Resource]
     * @constructor Creates a factory constructor for [Status.ERROR] Status
     */
    data class Error<out T>(private val data: T? = null) :
        Resource<T>(Status.ERROR, data?.let { Event(it) })

    /**
     * Data Class for [Status.LOADING] Status
     *
     * @property data The content to be wrapped in the [dataEvent] of the [Resource]
     * @constructor Creates a factory constructor for [Status.LOADING] Status
     */
    data class Loading<out T>(private val data: T? = null) :
        Resource<T>(Status.LOADING, data?.let { Event(it) })

    /**
     * Data Class for [Status.UNKNOWN] Status
     *
     * @property data The content to be wrapped in the [dataEvent] of the [Resource]
     * @constructor Creates a factory constructor for [Status.UNKNOWN] Status
     */
    data class Unknown<out T>(private val data: T? = null) :
        Resource<T>(Status.UNKNOWN, data?.let { Event(it) })

    /**
     * Reads and returns the content wrapped in [dataEvent]. Can be `null` if content [T] was `null`.
     */
    fun peekData(): T? = dataEvent?.peekContent()
}

/**
 * Enum class for the Status on the data wrapped in the [Resource].
 *
 * @author Kaushik N Sanji
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    UNKNOWN
}