@file:JvmName("LessonScaffoldKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScaffold(
    lesson: LessonDefinition,
    completed: Boolean,
    onBack: () -> Unit,
    onComplete: (() -> Unit)?,
    onOpenLab: (() -> Unit)? = null,
    tryContent: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lesson.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { inner ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                top = inner.calculateTopPadding() + 12.dp,
                end = 16.dp,
                bottom = inner.calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Text(
                    text = lesson.moduleTitle,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(text = lesson.summary, style = MaterialTheme.typography.bodyLarge)
            }
            item { LessonTextSection(title = "Concept", body = lesson.concept) }
            item {
                LessonCard {
                    LessonTextSection(title = "Try", body = "Tuong tac truc tiep voi vi du ben duoi.")
                    tryContent()
                }
            }
            item { LessonTextSection(title = "Observe", body = lesson.observe) }
            item { LessonTextSection(title = "Challenge", body = lesson.challenge) }
            item { LessonTextSection(title = "Summary", body = lesson.wrapUp) }
            item {
                LessonActions(
                    completed = completed,
                    onComplete = onComplete,
                    onOpenLab = onOpenLab,
                )
            }
        }
    }
}

@Composable
private fun LessonActions(
    completed: Boolean,
    onComplete: (() -> Unit)?,
    onOpenLab: (() -> Unit)?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onComplete != null) {
            Button(onClick = onComplete, enabled = !completed) {
                if (completed) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                }
                Text(if (completed) "Completed" else "Mark complete", modifier = Modifier.padding(start = 4.dp))
            }
        }
        if (onOpenLab != null) {
            OutlinedButton(onClick = onOpenLab) {
                Text("Open lab")
            }
        }
    }
}
