package com.creative.androidfundamentalsbydantech.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.creative.androidfundamentalsbydantech.ui.launchmodes.LaunchModesContent
import com.creative.androidfundamentalsbydantech.ui.theme.AppTheme

class StandardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate task=$taskId instance=${hashCode()}")
        setContent {
            AppTheme {
                LaunchModesContent(
                    title = "Standard",
                    subtitle = "android:launchMode=\"standard\"",
                    taskId = taskId,
                    instanceHash = hashCode(),
                    onBack = { finish() },
                )
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }

    companion object {
        private const val TAG = "StandardActivity"
    }
}
