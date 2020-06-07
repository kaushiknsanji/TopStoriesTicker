package com.kaushiknsanji.topstoriesticker.ui.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.*
import com.kaushiknsanji.topstoriesticker.ui.base.listeners.ListenerObservable

/**
 * An abstract base class for [RecyclerView.Adapter] that registers as a [LifecycleObserver]
 * on the [Lifecycle] of a LifecycleOwner to be Lifecycle aware. Provides abstraction for common tasks and setup.
 *
 * @param T The type of ItemView's data.
 * @param L The type of Listeners that extends [BaseAdapter.DefaultListener].
 * @param VH The type of ItemView's ViewHolder that extends [BaseItemViewHolder].
 * @param parentLifecycle The [Lifecycle] of a LifecycleOwner to observe on.
 * @param hostListener The Host of this Adapter that wishes to auto register/unregister as Listener of type [L]
 * for Navigation events. The Host should implement the listener of type [L] for this to work. Can be `null` if not required.
 * @property listenerObservable Instance of [ListenerObservable] that dispatches callback events to registered Listeners.
 *
 * @author Kaushik N Sanji
 */
abstract class BaseAdapter<T : Any, L : BaseAdapter.DefaultListener<T>, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    hostListener: L?,
    protected val listenerObservable: ListenerObservable<L>
) : RecyclerView.Adapter<VH>() {

    // For the RecyclerView instance
    private var recyclerView: RecyclerView? = null

    // Helper for computing the difference between two lists in a background thread
    private lateinit var differ: AsyncListDiffer<T>

    init {
        // Register an Observer on the parent's Lifecycle to keep the Adapter in-sync
        parentLifecycle.addObserver(object : DefaultLifecycleObserver {

            /**
             * Notifies that `ON_START` event occurred. This method will be called
             * after the [LifecycleOwner]'s `onStart` method returns.
             *
             * This should update the ViewHolder Lifecycle of only the visible ViewHolders to Started state.
             *
             * This is also the place to register a [hostListener] if available.
             *
             * @param owner the component, whose state was changed
             */
            override fun onStart(owner: LifecycleOwner) {
                recyclerView?.run {
                    val layoutManager = layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        // Finding the start-end Adapter positions of the Visible set from the LinearLayoutManager
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        // Updating the visible ViewHolders in the range to Started state
                        (firstVisibleItemPosition..lastVisibleItemPosition).forEach { adapterPosition ->
                            findViewHolderForAdapterPosition(adapterPosition)?.let { viewHolder ->
                                (viewHolder as BaseItemViewHolder<*, *>).onStart()
                            }
                        }
                    }
                }

                // If the Host is also a Listener and is available, then register it during `onStart` event
                hostListener?.let { listenerObservable.registerListener(it) }
            }

            /**
             * Notifies that `ON_STOP` event occurred. This method will be called
             * before the [LifecycleOwner]'s `onStop` method is called.
             *
             * This should update the ViewHolder Lifecycle of all the ViewHolders to Stopped state.
             *
             * This is also the place to unregister a previously registered [hostListener].
             *
             * @param owner the component, whose state was changed
             */
            override fun onStop(owner: LifecycleOwner) {
                recyclerView?.run {
                    (0 until childCount).forEach { childIndex ->
                        getChildAt(childIndex)?.let { childView ->
                            (getChildViewHolder(childView) as BaseItemViewHolder<*, *>).onStop()
                        }
                    }
                }

                // If the Host is also a Listener and is available, then unregister it during `onStop` event
                hostListener?.let { listenerObservable.unregisterListener(it) }
            }

            /**
             * Notifies that `ON_DESTROY` event occurred. This method will be called
             * before the [LifecycleOwner]'s `onDestroy` method is called.
             *
             * This should update the ViewHolder Lifecycle of all the ViewHolders to Destroyed state.
             *
             * @param owner the component, whose state was changed
             */
            override fun onDestroy(owner: LifecycleOwner) {
                recyclerView?.run {
                    (0 until childCount).forEach { childIndex ->
                        getChildAt(childIndex)?.let { childView ->
                            (getChildViewHolder(childView) as BaseItemViewHolder<*, *>).onDestroy()
                        }
                    }
                }
            }
        })
    }

    /**
     * Called by RecyclerView when it starts observing this Adapter.
     *
     * @param recyclerView The RecyclerView instance which started observing this adapter.
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // When the RecyclerView is attached to this adapter, save the reference
        this.recyclerView = recyclerView
        // Initialize the [differ]
        setupDiffer()
    }

    /**
     * Called when the [recyclerView] starts observing this Adapter.
     * Initializes the [differ] for computing the difference between two lists in a background thread.
     */
    private fun setupDiffer() {
        // Initialize [differ] with `ListUpdateCallback` and `AsyncDifferConfig`
        differ = AsyncListDiffer(
            object : ListUpdateCallback {
                /**
                 * Called when `count` number of items are updated at the given position.
                 *
                 * @param position The position of the item which has been updated.
                 * @param count    The number of items which has changed.
                 */
                override fun onChanged(position: Int, count: Int, payload: Any?) {
                    // Notify the changes to the adapter
                    notifyItemRangeChanged(position, count, payload)
                    // Rebuild item decorations on RecyclerView
                    invalidateItemDecorations()
                }

                /**
                 * Called when an item changes its position in the list.
                 *
                 * @param fromPosition The previous position of the item before the move.
                 * @param toPosition   The new position of the item.
                 */
                override fun onMoved(fromPosition: Int, toPosition: Int) {
                    // Notify the changes to the adapter
                    notifyItemMoved(fromPosition, toPosition)
                    // Rebuild item decorations on RecyclerView
                    invalidateItemDecorations()
                }

                /**
                 * Called when `count` number of items are inserted at the given position.
                 *
                 * @param position The position of the new item.
                 * @param count    The number of items that have been added.
                 */
                override fun onInserted(position: Int, count: Int) {
                    // Notify the changes to the adapter
                    notifyItemRangeInserted(position, count)
                    // Rebuild item decorations on RecyclerView
                    invalidateItemDecorations()
                }

                /**
                 * Called when `count` number of items are removed from the given position.
                 *
                 * @param position The position of the item which has been removed.
                 * @param count    The number of items which have been removed.
                 */
                override fun onRemoved(position: Int, count: Int) {
                    // Notify the changes to the adapter
                    notifyItemRangeRemoved(position, count)
                    // Rebuild item decorations on RecyclerView
                    invalidateItemDecorations()
                }

                /**
                 * Rebuilds the Item Decorations on [recyclerView].
                 */
                private fun invalidateItemDecorations() {
                    // Invalidate with a delay of 2ms, for the adapter item notifications to complete first
                    recyclerView?.run {
                        postDelayed(
                            { invalidateItemDecorations() },
                            2
                        )
                    }
                }

            },
            // Build AsyncDifferConfig instance with the provided `DiffUtil.ItemCallback` implementation
            AsyncDifferConfig.Builder(provideItemCallback()).build()
        )
    }

    /**
     * Called by RecyclerView when it stops observing this Adapter.
     *
     * @param recyclerView The RecyclerView instance which stopped observing this adapter.
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        // When the RecyclerView is detached, the Lifecycle of all ViewHolders should be Destroyed.
        this.recyclerView?.run {
            (0 until childCount).forEach { childIndex ->
                getChildAt(childIndex)?.let { childView ->
                    (getChildViewHolder(childView) as BaseItemViewHolder<*, *>).onDestroy()
                }
            }
        }

        // As the RecyclerView is detached from the Adapter, clear the reference.
        this.recyclerView = null

    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     *
     * This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * [onViewDetachedFromWindow] those resources should be restored here.
     *
     * @param holder Holder of the view being attached
     */
    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        // When the ItemView is attached from Window, it becomes visible.
        // Hence the ViewHolder Lifecycle should be started
        holder.onStart()
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     *
     * Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.
     *
     * @param holder Holder of the view being detached
     */
    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        // When the ItemView is detached from Window, it goes into the background (not visible).
        // Hence the ViewHolder Lifecycle should be stopped
        holder.onStop()
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [androidx.recyclerview.widget.RecyclerView.ViewHolder.itemView] to reflect
     * the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        // Delegate to the holder to bind the data to the ItemView at the position
        holder.bind(getItem(position))
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = differ.currentList.size

    /**
     * To be overridden by subclasses to provide the [DiffUtil.ItemCallback] instance for
     * calculating the difference between two non-null items in a List.
     */
    protected abstract fun provideItemCallback(): DiffUtil.ItemCallback<T>

    /**
     * Submits a new [list] to be diffed, and displayed.
     *
     * If a list is already being displayed, a difference will be computed on a background thread, which
     * will dispatch `Adapter.notifyItem` events on the main thread.
     */
    fun submitList(list: List<T>?) {
        differ.submitList(list)
    }

    /**
     * Retrieves the item at the specified [position] in the list, managed by [differ].
     */
    private fun getItem(position: Int): T = differ.currentList[position]

    /**
     * Interface to be implemented by the Host of this Adapter to receive callback events.
     *
     * @param T The type of ItemView's data.
     */
    interface DefaultListener<T> {
        /**
         * Callback Method of [BaseAdapter.DefaultListener] invoked when the user clicks on the Adapter Item.
         *
         * @param itemData Data of the Adapter Item.
         */
        fun onItemClick(itemData: T)
    }
}