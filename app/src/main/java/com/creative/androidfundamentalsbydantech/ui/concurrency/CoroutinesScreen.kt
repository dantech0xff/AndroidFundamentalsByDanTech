package com.creative.androidfundamentalsbydantech.ui.concurrency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CoroutinesScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()

    DemoScaffold(title = "Coroutines", onBack = onBack) {
        SectionHeader("launch vs async + await")
        Explanation("launch fire-and-forget; async trả Deferred<T> để await giá trị.")
        LaunchVsAsyncDemo(scope)

        SectionHeader("withContext: đổi Dispatcher")
        Explanation("Dispatchers.IO cho blocking I/O, Default cho CPU, Main cho UI.")
        WithContextDemo(scope)

        SectionHeader("Cancellation + isActive")
        Explanation("Cancel job đang chạy; kiểm tra isActive để dừng gọn gàng.")
        CancellationDemo(scope)

        SectionHeader("CoroutineExceptionHandler")
        Explanation("Bắt exception không xử lý trên root coroutine.")
        ExceptionHandlerDemo()

        SectionHeader("SupervisorJob")
        Explanation("Một child fail không kéo theo siblings.")
        SupervisorDemo()
    }
}

@Composable
private fun LaunchVsAsyncDemo(scope: CoroutineScope) {
    var log by remember { mutableStateOf("—") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch {
                    val sum = coroutineScope {
                        val a = async { slowValue(1, 300) }
                        val b = async { slowValue(2, 500) }
                        awaitAll(a, b).sum()
                    }
                    log = "async+await -> $sum"
                }
            }) { Text("Run parallel") }
            Text(log, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

private suspend fun slowValue(v: Int, ms: Long): Int {
    delay(ms)
    return v
}

@Composable
private fun WithContextDemo(scope: CoroutineScope) {
    var status by remember { mutableStateOf("idle") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch {
                    status = "Main: reading…"
                    val data = withContext(Dispatchers.IO) {
                        Thread.sleep(400) // pretend blocking I/O
                        "payload"
                    }
                    status = "Got [$data] on Main"
                }
            }) { Text("Fetch") }
            Text(status, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun CancellationDemo(scope: CoroutineScope) {
    var job by remember { mutableStateOf<Job?>(null) }
    var counter by remember { mutableStateOf(0) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                job?.cancel()
                counter = 0
                job = scope.launch {
                    while (isActive) {
                        delay(300)
                        counter++
                    }
                }
            }) { Text("Start") }
            Button(onClick = { job?.cancel(); job = null }) { Text("Cancel") }
            Text("counter=$counter", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun ExceptionHandlerDemo() {
    var message by remember { mutableStateOf("—") }
    val handler = remember {
        CoroutineExceptionHandler { _, ex -> message = "caught: ${ex.message}" }
    }
    val scope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main + handler) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch { throw IllegalStateException("boom") }
            }) { Text("Throw") }
            Text(message, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun SupervisorDemo() {
    var log by remember { mutableStateOf("—") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val supervisor = CoroutineScope(SupervisorJob() + Dispatchers.Main)
                val messages = mutableListOf<String>()
                supervisor.launch(CoroutineExceptionHandler { _, ex ->
                    messages += "child-1 failed: ${ex.message}"
                    log = messages.joinToString("\n")
                }) {
                    delay(100)
                    throw IllegalStateException("sibling dies")
                }
                supervisor.launch {
                    delay(300)
                    messages += "child-2 survived"
                    log = messages.joinToString("\n")
                }
            }) { Text("Fire two") }
            Text(log, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
