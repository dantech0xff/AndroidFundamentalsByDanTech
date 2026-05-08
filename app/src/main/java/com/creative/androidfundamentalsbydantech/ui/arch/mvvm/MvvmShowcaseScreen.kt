package com.creative.androidfundamentalsbydantech.ui.arch.mvvm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidfundamentalsbydantech.ui.learning.LearningContent
import com.creative.androidfundamentalsbydantech.ui.learning.LessonScaffold

@Composable
fun MvvmShowcaseScreen(
    onBack: () -> Unit,
    completed: Boolean = false,
    onComplete: (() -> Unit)? = null,
    onOpenLab: (() -> Unit)? = null,
    vm: SimpleMvvmViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val input = remember { mutableStateOf("") }
    val lesson = requireNotNull(LearningContent.lessonById(LearningContent.VIEWMODEL_UDF))

    LessonScaffold(
        lesson = lesson,
        completed = completed,
        onBack = onBack,
        onComplete = onComplete,
        onOpenLab = onOpenLab,
    ) {
        Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (state.busy) "ViewModel is working…" else "State down, events up",
                    style = MaterialTheme.typography.titleMedium,
                )
                OutlinedTextField(
                    value = input.value,
                    onValueChange = { input.value = it },
                    label = { Text("Plain text") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { vm.onEncode(input.value) }, enabled = !state.busy) {
                        Text("Encode")
                    }
                    OutlinedButton(
                        onClick = { vm.onClear() },
                        enabled = !state.busy && state.history.isNotEmpty(),
                    ) {
                        Text("Clear")
                    }
                }
            }
        }

        Text(
            text = "History (${state.history.size})",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp),
        )
        state.history.forEach { entry ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(entry.plain, style = MaterialTheme.typography.bodyLarge)
                    Text(entry.encoded, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
