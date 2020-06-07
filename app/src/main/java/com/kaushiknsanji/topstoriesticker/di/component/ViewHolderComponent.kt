package com.kaushiknsanji.topstoriesticker.di.component

import com.kaushiknsanji.topstoriesticker.di.ViewHolderScope
import com.kaushiknsanji.topstoriesticker.di.module.ViewHolderModule
import dagger.Component

/**
 * Dagger Component for exposing dependencies from the Module [ViewHolderModule]
 * and its component [ApplicationComponent] dependency.
 *
 * @author Kaushik N Sanji
 */
@ViewHolderScope
@Component(dependencies = [ApplicationComponent::class], modules = [ViewHolderModule::class])
interface ViewHolderComponent {


}