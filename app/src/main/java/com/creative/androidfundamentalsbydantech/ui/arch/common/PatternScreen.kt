package com.creative.androidfundamentalsbydantech.ui.arch.common

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader

/**
 * Shared shell for the 5 architecture-pattern demos. Each pattern wires up
 * an encode action + a clear action + a history list — only the plumbing
 * behind those three behaviours differs.
 */
@Composable
fun PatternScreen(
    title: String,
    tagline: String,
    input: MutableState<String>,
    history: List<ArchHistoryEntry>,
    onBack: () -> Unit,
    onEncode: () -> Unit,
    onClear: () -> Unit,
    tradeoffs: List<String>,
) {
    DemoScaffold(title = title, onBack = onBack) {
        SectionHeader("What this pattern is")
        Explanation(tagline)

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = input.value,
                    onValueChange = { input.value = it },
                    label = { Text("Plain text") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onEncode) { Text("Encode (Base64)") }
                    OutlinedButton(onClick = onClear) { Text("Clear history") }
                }
            }
        }

        SectionHeader("History (${history.size})")
        if (history.isEmpty()) {
            Text("— empty —", style = MaterialTheme.typography.bodyLarge)
        } else {
            history.forEach { entry ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(entry.plain, style = MaterialTheme.typography.titleLarge)
                        Text(
                            entry.encoded.ifEmpty { "(empty)" },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        SectionHeader("Trade-offs")
        tradeoffs.forEach { Explanation("• $it") }
    }
}
