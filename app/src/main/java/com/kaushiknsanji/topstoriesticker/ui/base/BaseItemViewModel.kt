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