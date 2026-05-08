package com.creative.androidfundamentalsbydantech.ui.concurrency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun FlowScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Flow / StateFlow / SharedFlow", onBack = onBack) {
        SectionHeader("Cold Flow")
        Explanation("Mỗi collector chạy lại pipeline từ đầu.")
        ColdFlowDemo()

        SectionHeader("StateFlow")
        Explanation("Hot, luôn có value hiện tại, conflate — lý tưởng cho UI state.")
        StateFlowDemo()

        SectionHeader("SharedFlow")
        Explanation("Hot, replay cấu hình được, dùng cho 1-time events (navigate, toast).")
        SharedFlowDemo()

        SectionHeader("Operator debounce / map / collectLatest")
        Explanation("Gõ search — kết quả chỉ compute sau khi ngừng gõ 300ms.")
        SearchDebounceDemo()
    }
}

@Composable
private fun ColdFlowDemo() {
    var collectedA by remember { mutableStateOf(0) }
    var collectedB by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val cold = remember {
        flow {
            repeat(5) {
                emit(it)
                delay(200)
            }
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch { cold.collect { collectedA = it } }
                scope.launch { cold.collect { collectedB = it } }
            }) { Text("Run both") }
            Text("A=$collectedA  B=$collectedB")
        }
    }
}

@Composable
private fun StateFlowDemo() {
    val state = remember { MutableStateFlow(0) }
    val current by state.collectAsState()
    var seen by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) { state.collect { seen = it } }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { state.value = state.value + 1 }) { Text("emit") }
            Text("value=$current  collected=$seen")
        }
    }
}

@Composable
private fun SharedFlowDemo() {
    val events = remember { MutableSharedFlow<String>(replay = 0) }
    var lastEvent by remember { mutableStateOf("—") }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) { events.collect { lastEvent = it } }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch { events.emit("event@${System.currentTimeMillis() % 1000}") }
            }) { Text("emit event") }
            Text(lastEvent)
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun SearchDebounceDemo() {
    var query by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("—") }
    val queries = remember { MutableStateFlow("") }
    LaunchedEffect(Unit) {
        queries
            .debounce(300)
            .map { it.trim() }
            .collectLatest { q ->
                if (q.isEmpty()) {
                    result = "—"
                    return@collectLatest
                }
                result = "searching…"
                delay(200)
                result = "$q -> ${q.length} chars"
            }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    queries.value = it
                },
                label = { Text("type to search") },
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
