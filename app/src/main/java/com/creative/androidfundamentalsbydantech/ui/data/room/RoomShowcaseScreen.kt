package com.creative.androidfundamentalsbydantech.ui.data.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidfundamentalsbydantech.data.local.NoteEntity
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import com.creative.androidfundamentalsbydantech.ui.learning.LearningContent
import com.creative.androidfundamentalsbydantech.ui.learning.LessonScaffold

@Composable
fun RoomShowcaseScreen(
    onBack: () -> Unit,
    completed: Boolean = false,
    onComplete: (() -> Unit)? = null,
    onOpenLab: (() -> Unit)? = null,
    vm: RoomViewModel = hiltViewModel(),
) {
    val notes by vm.notes.collectAsStateWithLifecycle()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val lesson = requireNotNull(LearningContent.lessonById(LearningContent.ROOM_PERSISTENCE))

    LessonScaffold(
        lesson = lesson,
        completed = completed,
        onBack = onBack,
        onComplete = onComplete,
        onOpenLab = onOpenLab,
    ) {
        SectionHeader("Add Note")
        Explanation("Flow-backed DAO — UI thread observes, insert chạy trên coroutine worker.")

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            if (title.isNotBlank() || body.isNotBlank()) {
                                vm.add(title.ifBlank { "Untitled" }, body)
                                title = ""
                                body = ""
                            }
                        },
                    ) { Text("Insert") }
                    OutlinedButton(onClick = { vm.clear() }) { Text("Clear all") }
                }
            }
        }

        SectionHeader("Notes (${notes.size})")
        if (notes.isEmpty()) {
            Text("— empty —", style = MaterialTheme.typography.bodyLarge)
        } else {
            notes.forEach { NoteCard(note = it, onPin = { vm.togglePin(it) }, onDelete = { vm.delete(it) }) }
        }
    }
}

@Composable
private fun NoteCard(note: NoteEntity, onPin: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(note.title, style = MaterialTheme.typography.titleLarge)
                if (note.body.isNotBlank()) {
                    Text(note.body, style = MaterialTheme.typography.bodyLarge)
                }
                Text(
                    "id=${note.id}  pinned=${note.pinned}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onPin) {
                Icon(
                    Icons.Default.PushPin,
                    contentDescription = "Pin",
                    tint = if (note.pinned) MaterialTheme.colorScheme.primary else Color.Gray,
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
