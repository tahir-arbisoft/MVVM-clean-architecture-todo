package com.kotlin.mvvm.kt.presentation.base

import android.app.Activity
import androidx.databinding.DataBindingComponent

/**
 * A Data Binding Component implementation for fragments
 */
class MyDataBindingComponent constructor(activity: Activity) : DataBindingComponent {


    override fun getActivityBindingAdapters() = activityAdapter

    private val activityAdapter = ActivityBindingAdapters(activity)
}