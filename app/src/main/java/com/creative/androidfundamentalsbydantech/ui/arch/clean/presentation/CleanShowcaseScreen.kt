package com.creative.androidfundamentalsbydantech.ui.arch.clean.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader

@Composable
fun CleanShowcaseScreen(onBack: () -> Unit, vm: CleanViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()
    var label by remember { mutableStateOf("") }
    var priority by remember { mutableIntStateOf(3) }

    DemoScaffold(title = "Clean Architecture", onBack = onBack) {
        SectionHeader("Domain / Data / Presentation")
        Explanation(
            "Domain layer (WishlistItem + WishlistRepository interface + UseCases) " +
                "không biết Android. Data layer (InMemoryWishlistRepository) cung cấp " +
                "implementation. Presentation layer (CleanViewModel) gọi use case, expose " +
                "UiState. Hilt @Binds nối dependency inversion.",
        )

        state.error?.let { err ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "error: $err",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.error,
                    )
                    IconButton(onClick = { vm.dismissError() }) {
                        Icon(Icons.Default.Close, contentDescription = "Dismiss")
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Wishlist item") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Priority: $priority (1..5)")
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { priority = (priority - 1).coerceAtLeast(1) }) {
                            Text("-", style = MaterialTheme.typography.titleLarge)
                        }
                        IconButton(onClick = { priority = (priority + 1).coerceAtMost(5) }) {
                            Text("+", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
                Button(
                    onClick = {
                        vm.add(label, priority)
                        if (label.isNotBlank()) label = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) { Text("Add") }
            }
        }

        SectionHeader("Items (${state.items.size}) — sorted by priority")
        if (state.items.isEmpty()) {
            Text("— empty —", style = MaterialTheme.typography.bodyLarge)
        } else {
            state.items.forEach { item ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.label, style = MaterialTheme.typography.titleLarge)
                            Text(
                                "priority=${item.priority}  id=${item.id}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = { vm.remove(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                    }
                }
            }
        }

        SectionHeader("Trade-offs")
        Explanation("• Layer boundary + interface -> dễ mock, dễ test use case độc lập.")
        Explanation("• Nhiều file / boilerplate hơn; over-kill cho app nhỏ.")
        Explanation("• Thay data source (Room/Retrofit) không sửa domain hay presentation.")
    }
}
