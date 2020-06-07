package com.kaushiknsanji.topstoriesticker.di.module

import androidx.lifecycle.LifecycleRegistry
import com.kaushiknsanji.topstoriesticker.di.ViewHolderScope
import com.kaushiknsanji.topstoriesticker.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

/**
 * Dagger Module for creating and exposing dependencies, tied to the RecyclerView's ViewHolder Lifecycle.
 *
 * @author Kaushik N Sanji
 */
@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    /**
     * Provides Singleton instance of [LifecycleRegistry] for [BaseItemViewHolder]
     */
    @ViewHolderScope
    @Provides
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)

}