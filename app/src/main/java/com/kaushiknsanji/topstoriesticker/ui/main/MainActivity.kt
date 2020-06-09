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

package com.kaushiknsanji.topstoriesticker.ui.main

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaushiknsanji.topstoriesticker.R
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.di.component.ActivityComponent
import com.kaushiknsanji.topstoriesticker.ui.base.BaseActivity
import com.kaushiknsanji.topstoriesticker.ui.main.news.NewsAdapter
import com.kaushiknsanji.topstoriesticker.utils.common.IntentUtility
import com.kaushiknsanji.topstoriesticker.utils.common.observeEvent
import com.kaushiknsanji.topstoriesticker.utils.common.observeNonNull
import com.kaushiknsanji.topstoriesticker.utils.widget.VerticalListItemSpacingDecoration
import com.kaushiknsanji.topstoriesticker.utils.widget.getFirstVisibleItemPosition
import com.kaushiknsanji.topstoriesticker.utils.widget.smoothVScrollToPositionWithViewTop
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * [BaseActivity] subclass that inflates the layout 'R.layout.activity_main' to show the Main screen.
 * [MainViewModel] is the primary [androidx.lifecycle.ViewModel] of this Activity.
 *
 * @author Kaushik N Sanji
 */
class MainActivity : BaseActivity<MainViewModel>(), NewsAdapter.Listener {

    companion object {
        // Constant used for logs
        private const val TAG = "MainActivity"
    }

    // LinearLayoutManager instance provided by Dagger
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    // NewsAdapter instance provided by Dagger
    @Inject
    lateinit var newsAdapter: NewsAdapter

    /**
     * Injects dependencies exposed by [ActivityComponent] into Activity.
     */
    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /**
     * Provides the Resource Layout Id for the Activity.
     */
    override fun provideLayoutId(): Int = R.layout.activity_main

    /**
     * Initializes the Layout of the Activity.
     */
    override fun setupView(savedInstanceState: Bundle?) {
        // Setting up RecyclerView
        rv_main.apply {
            // Set the Layout Manager to LinearLayoutManager
            layoutManager = linearLayoutManager
            // Set the Adapter for RecyclerView
            adapter = newsAdapter
            // Add List Item Spacing Decoration
            addItemDecoration(
                VerticalListItemSpacingDecoration(
                    resources.getDimensionPixelSize(R.dimen.list_item_spacing)
                )
            )
        }

        // Setting up SwipeRefreshLayout
        swipe_refresh_main.apply {
            // Configure the progress circle indicator colors
            setColorSchemeResources(
                R.color.colorPink500,
                R.color.colorPurple500,
                R.color.colorIndigo500,
                R.color.colorGreen500
            )

            // Register a Listener on the Swipe refresh
            setOnRefreshListener {
                // Delegate to the ViewModel to refresh the content
                viewModel.onRefresh()
            }
        }
    }

    /**
     * Method that initializes the [androidx.lifecycle.LiveData] observers.
     * Can be overridden by subclasses to initialize other [androidx.lifecycle.LiveData] observers.
     */
    override fun setupObservers() {
        super.setupObservers()

        // Register an observer on the New Articles Event, to submit it to the Adapter
        viewModel.liveArticles.observeEvent(this) { articles: List<NewsArticle> ->
            newsAdapter.submitList(articles)
        }

        // Register an observer on Downloading Progress LiveData, to show the loading indication on the SwipeRefreshLayout
        viewModel.loadingProgress.observeNonNull(this) { state: Boolean ->
            swipe_refresh_main.isRefreshing = state
        }

        // Register an observer for Scroll to Top Event, to scroll to the topmost item in the RecyclerView
        viewModel.scrollToTop.observeEvent(this) {
            Handler().postDelayed({
                rv_main.smoothScrollToPosition(0)
            }, 5) // Scroll to top after 5ms
        }

        // Register an observer on News Article launch Events, to open the same in a Browser
        viewModel.launchArticle.observeEvent(this) { article: NewsArticle ->
            // Delegate to the utility to handle
            IntentUtility.openLink(this, article.webUrl)
        }

        // Register an observer on Last Scrolled Position Events to restore the
        // scrolled position on RecyclerView
        viewModel.lastScrolledPosition.observeEvent(this) { scrollToPosition: Int ->
            // Scroll to top after 10ms in order to allow the RecyclerView to load the list
            Handler().postDelayed({
                rv_main.smoothVScrollToPositionWithViewTop(scrollToPosition)
            }, 10)
        }

    }

    /**
     * Callback Method of [NewsAdapter.Listener] invoked when the user clicks on the Adapter Item.
     *
     * @param itemData Data of the Adapter Item.
     */
    override fun onItemClick(itemData: NewsArticle) {
        viewModel.onItemClick(itemData)
    }

    /**
     * Called when no longer visible to the user.
     */
    override fun onStop() {
        super.onStop()

        // Delegate to the ViewModel to save and restore the scrolled position later,
        // only if the position is valid
        rv_main.getFirstVisibleItemPosition().takeIf { it > RecyclerView.NO_POSITION }?.let {
            viewModel.saveLastScrolledPosition(it)
        }

    }

}