package com.creative.androidfundamentalsbydantech.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.creative.androidfundamentalsbydantech.ui.launchmodes.LaunchModesContent
import com.creative.androidfundamentalsbydantech.ui.theme.AppTheme

class SingleTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate task=$taskId instance=${hashCode()}")
        setContent {
            AppTheme {
                LaunchModesContent(
                    title = "SingleTask",
                    subtitle = "android:launchMode=\"singleTask\"",
                    taskId = taskId,
                    instanceHash = hashCode(),
                    onBack = { finish() },
                )
            }
        }
    }

    companion object {
        private const val TAG = "SingleTaskActivity"
    }
}
