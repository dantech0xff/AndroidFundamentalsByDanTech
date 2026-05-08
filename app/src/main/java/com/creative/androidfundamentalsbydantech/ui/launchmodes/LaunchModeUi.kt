package com.creative.androidfundamentalsbydantech.ui.launchmodes

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.activity.SingleInstanceActivity
import com.creative.androidfundamentalsbydantech.activity.SingleTaskActivity
import com.creative.androidfundamentalsbydantech.activity.SingleTopActivity
import com.creative.androidfundamentalsbydantech.activity.StandardActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchModesHubScreen(onBack: () -> Unit) {
    LaunchModesContent(
        title = "Launch Modes",
        subtitle = "Hub",
        taskId = null,
        instanceHash = null,
        onBack = onBack,
    )
}

/**
 * Shared UI for both the Compose NavHost hub entry and each standalone
 * launch-mode activity. When [taskId] / [instanceHash] are non-null the
 * screen is being rendered by a real activity instance (so we show them).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchModesContent(
    title: String,
    subtitle: String,
    taskId: Int?,
    instanceHash: Int?,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val modeNames = remember {
        listOf(
            "Standard" to StandardActivity::class.java,
            "SingleTop" to SingleTopActivity::class.java,
            "SingleTask" to SingleTaskActivity::class.java,
            "SingleInstance" to SingleInstanceActivity::class.java,
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleLarge,
            )
            if (taskId != null && instanceHash != null) {
                Text(
                    text = "Task Id: $taskId\nInstance Hash: $instanceHash",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                Text(
                    text = "Mở từng mode ở dưới để xem Task Id / Instance Hash thay đổi thế nào khi bạn quay lại rồi bấm lại.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            modeNames.forEach { (label, clazz) ->
                Button(
                    onClick = { context.startActivity(Intent(context, clazz)) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Start $label Activity")
                }
            }
        }
    }
}

