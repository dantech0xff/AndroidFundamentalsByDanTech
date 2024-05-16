package com.creative.androidfundamentalsbydantech

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * Created by dan on 16/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "onCreate")
    }
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d("MyApplication", "onTrimMemory")
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("MyApplication", "onConfigurationChanged")
    }
    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("MyApplication", "onLowMemory")
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d("MyApplication", "attachBaseContext")
        ProcessLifecycleOwner.get().lifecycle.addObserver(object: LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("MyApplication", "onStateChanged - $event")
            }
        })
    }
}

