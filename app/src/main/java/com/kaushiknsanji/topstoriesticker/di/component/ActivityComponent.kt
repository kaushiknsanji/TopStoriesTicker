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

package com.kaushiknsanji.topstoriesticker.di.component

import com.kaushiknsanji.topstoriesticker.di.ActivityScope
import com.kaushiknsanji.topstoriesticker.di.module.ActivityModule
import com.kaushiknsanji.topstoriesticker.ui.main.MainActivity
import dagger.Component

/**
 * Dagger Component for exposing dependencies from the Module [ActivityModule]
 * and its component [ApplicationComponent] dependency.
 *
 * @author Kaushik N Sanji
 */
@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    /**
     * Injects dependencies into [MainActivity] instance.
     */
    fun inject(activity: MainActivity)

}