@file:JvmName("MissingLessonScreenKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold

@Composable
fun MissingLessonScreen(lessonId: String, onBack: () -> Unit) {
    DemoScaffold(title = "Lesson not found", onBack = onBack, scrollable = false) { padding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Unknown lesson: $lessonId")
        }
    }
}
