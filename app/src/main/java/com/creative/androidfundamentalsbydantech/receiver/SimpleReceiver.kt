package com.creative.androidfundamentalsbydantech.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.creative.androidfundamentalsbydantech.service.ForegroundService

/**
 * Created by dan on 16/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleReceiver : BroadcastReceiver() {
    companion object {
        const val SIMPLE_ACTION = "com.creative.androidfundamentals.SIMPLE_ACTION"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("SimpleReceiver", "onReceive - ${intent?.action}")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val foregroundServiceIntent = Intent(context, ForegroundService::class.java).putExtra("id", 999)
                .putExtra("dataField", "data_receiver")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context?.startForegroundService(foregroundServiceIntent)
            } else {
                context?.startService(foregroundServiceIntent)
            }
        } else if (intent?.action == SIMPLE_ACTION) {
            // Do something
            Log.d("SimpleReceiver", "SIMPLE_ACTION RECEIVED")
        }
    }
}


