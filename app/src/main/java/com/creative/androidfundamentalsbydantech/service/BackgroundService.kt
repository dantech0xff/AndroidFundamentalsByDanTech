package com.creative.androidfundamentalsbydantech.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Created by dan on 16/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class BackgroundService : Service() {
    private var job: Job? = null
    private val sharedPrefFile = "com.creative.androidfundamentals.backgroundservice"
    override fun onCreate() {
        super.onCreate()
        Log.d("BackgroundService", "onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BackgroundService", "onStartCommand")
        runBackgroundTask(intent?.getStringExtra("dataField") ?: "data_0")
        return START_NOT_STICKY
    }

    private fun runBackgroundTask(dataField: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive && job?.isActive == true) {
                // Giả sử đây là một công việc nặng cần chạy ở Background
                val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                val data = sharedPref.getInt(dataField, 0)
                sharedPref.edit().putInt(dataField, data + 1).apply()
                Log.d("BackgroundService", "$dataField - $data - ${this@BackgroundService}")
                delay(3000)
            }
        }
    }

    override fun onDestroy() {
        Log.d("BackgroundService", "onDestroy")
        super.onDestroy()
        job?.cancel()
        job = null
    }
}