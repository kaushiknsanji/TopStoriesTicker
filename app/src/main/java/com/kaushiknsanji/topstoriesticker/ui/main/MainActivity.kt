package com.kaushiknsanji.topstoriesticker.ui.main

import android.os.Bundle
import com.kaushiknsanji.topstoriesticker.R
import com.kaushiknsanji.topstoriesticker.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}