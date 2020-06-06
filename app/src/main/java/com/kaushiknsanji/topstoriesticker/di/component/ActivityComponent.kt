package com.kaushiknsanji.topstoriesticker.di.component

import com.kaushiknsanji.topstoriesticker.di.ActivityScope
import com.kaushiknsanji.topstoriesticker.di.module.ActivityModule
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

}