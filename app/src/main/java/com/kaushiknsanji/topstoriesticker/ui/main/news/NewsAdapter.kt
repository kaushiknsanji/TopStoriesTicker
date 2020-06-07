package com.kaushiknsanji.topstoriesticker.ui.main.news

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.ui.base.BaseAdapter
import com.kaushiknsanji.topstoriesticker.ui.main.news.NewsAdapter.Listener

/**
 * [BaseAdapter] subclass for the [androidx.recyclerview.widget.RecyclerView]
 * shown in [com.kaushiknsanji.topstoriesticker.ui.main.MainActivity]
 *
 * @param parentLifecycle [Lifecycle] of [com.kaushiknsanji.topstoriesticker.ui.main.MainActivity]
 * @param hostListener The Host of this Adapter that wishes to auto register/unregister as [Listener]
 * for Navigation events. The Host should implement the [Listener] for this to work.
 * @constructor Instance of [NewsAdapter] created and provided by Dagger.
 *
 * @author Kaushik N Sanji
 */
class NewsAdapter(
    parentLifecycle: Lifecycle,
    hostListener: Listener?
) : BaseAdapter<NewsArticle, Listener, NewsItemViewHolder>(
    parentLifecycle,
    hostListener
) {

    /**
     * Provides the [DiffUtil.ItemCallback] instance for
     * calculating the difference between two non-null [NewsArticle] items in a List.
     */
    override fun provideItemCallback(): DiffUtil.ItemCallback<NewsArticle> =
        object : DiffUtil.ItemCallback<NewsArticle>() {
            /**
             * Called to check whether two objects represent the same [NewsArticle] item.
             *
             * For example, if your items have unique ids, this method should check their id equality.
             *
             * Note: `null` items in the list are assumed to be the same as another `null`
             * item and are assumed to not be the same as a non-`null` item. This callback will
             * not be invoked for either of those cases.
             *
             * @param oldArticleItem The [NewsArticle] item in the old list.
             * @param newArticleItem The [NewsArticle] item in the new list.
             * @return True if the two items represent the same [NewsArticle] or false if they are different.
             */
            override fun areItemsTheSame(
                oldArticleItem: NewsArticle,
                newArticleItem: NewsArticle
            ): Boolean = (oldArticleItem.id == newArticleItem.id)

            /**
             * Called to check whether two [NewsArticle] items have the same data.
             *
             * This information is used to detect if the contents of an item have changed.
             *
             * This method to check equality instead of [Object.equals] so that you can
             * change its behavior depending on your UI.
             *
             * For example, if you are using DiffUtil with a
             * [androidx.recyclerview.widget.RecyclerView.Adapter], you should
             * return whether the items' visual representations are the same.
             *
             * This method is called only if [areItemsTheSame] returns `true` for
             * these items.
             *
             * Note: Two `null` items are assumed to represent the same contents. This callback
             * will not be invoked for this case.
             *
             * @param oldArticleItem The [NewsArticle] item in the old list.
             * @param newArticleItem The [NewsArticle] item in the new list.
             * @return True if the contents of the [NewsArticle] items are the same or false if they are different.
             */
            override fun areContentsTheSame(
                oldArticleItem: NewsArticle,
                newArticleItem: NewsArticle
            ): Boolean = (oldArticleItem == newArticleItem)
        }

    /**
     * Called when RecyclerView needs a new [NewsItemViewHolder] of the given type to represent
     * an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new [NewsItemViewHolder] that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder =
        NewsItemViewHolder(parent, object : Listener {

            /**
             * Callback Method of [BaseAdapter.DefaultListener] invoked when the user clicks on the Adapter Item.
             *
             * @param itemData Data of the Adapter Item which is an instance of [NewsArticle].
             */
            override fun onItemClick(itemData: NewsArticle) {
                // Delegate to all registered listeners
                delegateEvent { listener: Listener -> listener.onItemClick(itemData) }
            }

            /**
             * Delegates the [event] to listeners registered in [listenerObservable].
             */
            inline fun delegateEvent(event: (Listener) -> Unit) {
                listenerObservable.getListeners().forEach(event)
            }

        })

    /**
     * Interface to be implemented by the Host [com.kaushiknsanji.topstoriesticker.ui.main.MainActivity]
     * to receive callback events.
     */
    interface Listener : DefaultListener<NewsArticle>
}