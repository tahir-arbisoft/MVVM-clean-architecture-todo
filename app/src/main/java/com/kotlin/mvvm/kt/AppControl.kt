package com.kotlin.mvvm.kt

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.kotlin.mvvm.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppControl : Application() {

    companion object {
        operator fun get(context: Context): AppControl {
            return context.applicationContext as AppControl
        }

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}
