package com.creative.androidfundamentalsbydantech.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.creative.androidfundamentalsbydantech.ui.launchmodes.LaunchModesContent
import com.creative.androidfundamentalsbydantech.ui.theme.AppTheme

class SingleTopActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate task=$taskId instance=${hashCode()}")
        setContent {
            AppTheme {
                LaunchModesContent(
                    title = "SingleTop",
                    subtitle = "android:launchMode=\"singleTop\"",
                    taskId = taskId,
                    instanceHash = hashCode(),
                    onBack = { finish() },
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent")
    }

    companion object {
        private const val TAG = "SingleTopActivity"
    }
}
