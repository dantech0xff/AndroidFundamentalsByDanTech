package com.creative.androidfundamentalsbydantech.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.creative.androidfundamentalsbydantech.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ForegroundService : Service() {

    private var job: Job? = null
    private val sharedPrefFile = "com.creative.androidfundamentals.foregroundservice"
    private val channelId = "ForegroundServiceChannel"

    override fun onCreate() {
        super.onCreate()
        Log.d("ForegroundService", "onCreate")
        createNotificationChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        job = null
        Log.d("ForegroundService", "onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = getNotification("Start Foreground Service")
        startForeground(intent?.getIntExtra("id", 0) ?: 0, notification)
        runBackgroundTask(
            intent?.getIntExtra("id", 0) ?: 0,
            intent?.getStringExtra("dataField") ?: "data_0"
        )
        return START_STICKY
    }
    private fun runBackgroundTask(id: Int, dataField: String) {
        job = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            while (isActive && job?.isActive == true) {
                // Assume this is a heavy task that needs to run in the background
                val sharedPref = getSharedPreferences(sharedPrefFile, android.content.Context.MODE_PRIVATE)
                val data = sharedPref.getInt(dataField, 0)
                sharedPref.edit().putInt(dataField, data + 1).apply()
                Log.d("ForegroundService", "$dataField - $data - ${this@ForegroundService}")
                try {
                    val notificationManager = getSystemService(NotificationManager::class.java)
                    notificationManager.notify(id, getNotification("$dataField - $data"))
                } catch (e: Exception) { }
                delay(3000)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            startForeground(1, getNotification("Foreground Service"))
        }
    }

    private fun getNotification(data: String): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("Data: $data")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }
}