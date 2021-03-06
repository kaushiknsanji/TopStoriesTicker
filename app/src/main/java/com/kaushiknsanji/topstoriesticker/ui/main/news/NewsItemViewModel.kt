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

package com.kaushiknsanji.topstoriesticker.ui.main.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.ui.base.BaseItemViewModel
import com.kaushiknsanji.topstoriesticker.utils.common.Event
import com.kaushiknsanji.topstoriesticker.utils.network.NetworkHelper
import javax.inject.Inject

/**
 * [BaseItemViewModel] subclass for [NewsItemViewHolder].
 *
 * @param networkHelper Instance of [NetworkHelper] provided by Dagger, to check
 * network connectivity status and handle network related issues.
 * @constructor Instance of [NewsItemViewModel] created and provided by Dagger.
 *
 * @author Kaushik N Sanji
 */
class NewsItemViewModel @Inject constructor(
    networkHelper: NetworkHelper
) : BaseItemViewModel<NewsArticle>(networkHelper) {

    // Transform the [itemData] to get the section name of News Article
    val sectionName: LiveData<String> =
        Transformations.map(itemData) { article -> article.sectionName }

    // Transform the [itemData] to get the published date of News Article
    val publishedDate: LiveData<String> =
        Transformations.map(itemData) { article -> article.publishedDate }

    // Transform the [itemData] to get the title of News Article
    val title: LiveData<String> = Transformations.map(itemData) { article -> article.newsTitle }

    // Transform the [itemData] to get the trail text of News Article
    val trailText: LiveData<String> =
        Transformations.map(itemData) { article -> article.fields.trailText }

    // Transform the [itemData] to get the author of News Article
    val author: LiveData<String> =
        Transformations.map(itemData) { article -> article.fields.author }

    // Transform the [itemData] to get the thumbnail of News Article
    val thumbnail: LiveData<String> =
        Transformations.map(itemData) { article -> article.fields.thumbnail }

    // LiveData for ItemView Click event
    private val _actionItemClick: MutableLiveData<Event<NewsArticle>> = MutableLiveData()
    val actionItemClick: LiveData<Event<NewsArticle>> = _actionItemClick

    // LiveData for Item Expand/Collapse button Click event
    private val _actionItemExpandCollapseClick: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val actionItemExpandCollapseClick: LiveData<Event<Boolean>> = _actionItemExpandCollapseClick

    // LiveData for Reset Content Expand State events to reset the Expand/Collapse button
    private val _resetContentExpandState: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val resetContentExpandState: LiveData<Event<Boolean>> = _resetContentExpandState

    /**
     * Callback method to be implemented, which will be called when this ViewModel's Activity/Fragment is created.
     */
    override fun onCreate() {
        // no-op
    }

    /**
     * Called when the user clicks on the News Article card.
     * Triggers an event to delegate it to the Host Listeners to handle.
     */
    fun onItemClick() {
        // Triggering the event when we have the News Article info
        itemData.value?.let {
            _actionItemClick.postValue(Event(it))
        }
    }

    /**
     * Called when the user clicks on the Expand/Collaspe anchor on the News Article card.
     * Triggers an event to [_actionItemExpandCollapseClick] handle the same.
     */
    fun onItemExpandCollapseClick() {
        // Triggering the event when we have the News Article info
        itemData.value?.let {
            _actionItemExpandCollapseClick.postValue(Event(true))
        }
    }

    /**
     * Called when the "Trail Text" and the "Author Text" is set on their TextView.
     * Triggers an Event to reset the Expand/Collapse Button based on the
     * presence of "Trail Text" content and "Author Text" length
     */
    fun onUpdateContentResetExpandState() {
        _resetContentExpandState.postValue(Event(true))
    }

}