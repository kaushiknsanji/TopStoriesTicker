package com.kaushiknsanji.topstoriesticker.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushiknsanji.topstoriesticker.R
import com.kaushiknsanji.topstoriesticker.di.component.ActivityComponent
import com.kaushiknsanji.topstoriesticker.ui.base.BaseActivity
import javax.inject.Inject

/**
 * [BaseActivity] subclass that inflates the layout 'R.layout.activity_main' to show the Main screen.
 * [MainViewModel] is the primary [androidx.lifecycle.ViewModel] of this Activity.
 *
 * @author Kaushik N Sanji
 */
class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        // Constant used for logs
        private const val TAG = "MainActivity"
    }

    // LinearLayoutManager instance provided by Dagger
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    /**
     * To be overridden by subclasses to inject dependencies exposed by [ActivityComponent] into Activity.
     */
    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /**
     * To be overridden by subclasses to provide the Resource Layout Id for the Activity.
     */
    override fun provideLayoutId(): Int = R.layout.activity_main

    /**
     * To be overridden by subclasses to setup the Layout of the Activity.
     */
    override fun setupView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    /**
     * Method that initializes the [androidx.lifecycle.LiveData] observers.
     * Can be overridden by subclasses to initialize other [androidx.lifecycle.LiveData] observers.
     */
    override fun setupObservers() {
        super.setupObservers()
    }

}