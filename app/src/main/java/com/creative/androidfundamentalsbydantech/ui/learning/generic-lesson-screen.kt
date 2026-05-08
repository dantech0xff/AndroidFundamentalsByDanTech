@file:JvmName("GenericLessonScreenKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GenericLessonScreen(
    lesson: LessonDefinition,
    completed: Boolean,
    onBack: () -> Unit,
    onComplete: () -> Unit,
    onOpenLab: (() -> Unit)?,
) {
    LessonScaffold(
        lesson = lesson,
        completed = completed,
        onBack = onBack,
        onComplete = onComplete,
        onOpenLab = onOpenLab,
    ) {
        if (onOpenLab == null) {
            Text("Lesson nay tap trung vao concept va challenge trong MVP dau tien.")
        } else {
            Button(onClick = onOpenLab) {
                Text("Open related lab")
            }
        }
    }
}
