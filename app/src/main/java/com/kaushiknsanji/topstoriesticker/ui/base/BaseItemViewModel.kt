package com.kaushiknsanji.topstoriesticker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper

/**
 * [BaseViewModel] abstract subclass for providing abstraction to common tasks of RecyclerView's ItemViews.
 *
 * @param T The type of ItemView's data.
 * @param networkHelper Instance of [NetworkHelper] provided by Dagger, to check
 * network connectivity status and handle network related issues.
 *
 * @author Kaushik N Sanji
 */
abstract class BaseItemViewModel<T : Any>(
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {

    // LiveData for ItemView's data
    protected val _itemData: MutableLiveData<T> = MutableLiveData()
    val itemData: LiveData<T> = _itemData

    /**
     * Posts the new [data] to the LiveData [_itemData]
     */
    fun updateItemData(data: T) {
        _itemData.postValue(data)
    }

    /**
     * Method to initiate the cleanup manually when the ItemView's ViewModel is no longer used.
     */
    fun onManualCleared() = onCleared()

}