package com.kaushiknsanji.topstoriesticker.di

import javax.inject.Scope

/**
 * Kotlin file for all the "Scope" annotations used in the app.
 *
 * @author Kaushik N Sanji
 */

/**
 * [Scope] annotation for scoping the dependencies
 * exposed by [com.kaushiknsanji.topstoriesticker.di.component.ActivityComponent]
 * and for distinguishing with the scoped dependencies exposed by its component
 * [com.kaushiknsanji.topstoriesticker.di.component.ApplicationComponent] dependency.
 */
@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityScope

/**
 * [Scope] annotation for scoping the dependencies
 * exposed by [com.kaushiknsanji.topstoriesticker.di.component.ViewHolderComponent]
 * and for distinguishing with the scoped dependencies exposed by its component
 * [com.kaushiknsanji.topstoriesticker.di.component.ApplicationComponent] dependency.
 */
@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ViewHolderScope