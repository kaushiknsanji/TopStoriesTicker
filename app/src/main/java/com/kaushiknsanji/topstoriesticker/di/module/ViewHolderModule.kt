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