@file:JvmName("QuestHomeScreenKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun QuestHomeScreen(
    onOpenLesson: (String) -> Unit,
    onOpenPath: () -> Unit,
    onOpenLab: () -> Unit,
    vm: QuestHomeViewModel = hiltViewModel(),
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    QuestHomeContent(
        state = state,
        onOpenLesson = onOpenLesson,
        onOpenPath = onOpenPath,
        onOpenLab = onOpenLab,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestHomeContent(
    state: QuestHomeUiState,
    onOpenLesson: (String) -> Unit,
    onOpenPath: () -> Unit,
    onOpenLab: () -> Unit,
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Android Fundamentals Quest") }) }) { inner ->
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
                LessonCard {
                    Text("Next lesson", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = state.nextLesson?.title ?: "All lessons completed",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = "${state.completedCount}/${state.totalCount} lessons completed",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    LinearProgressIndicator(
                        progress = { state.progressFraction },
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    )
                    state.nextLesson?.let { lesson ->
                        Button(
                            onClick = { onOpenLesson(lesson.id) },
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        ) { Text("Continue") }
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = onOpenPath, modifier = Modifier.weight(1f)) {
                        Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null)
                        Text("Path", modifier = Modifier.padding(start = 6.dp))
                    }
                    OutlinedButton(onClick = onOpenLab, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Science, contentDescription = null)
                        Text("Labs", modifier = Modifier.padding(start = 6.dp))
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Modules", style = MaterialTheme.typography.titleMedium)
                    state.modules.forEach { module ->
                        val lessonLabel = if (module.lessons.size == 1) "lesson" else "lessons"
                        Text(
                            text = "${module.title}: ${module.lessons.size} $lessonLabel",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}
