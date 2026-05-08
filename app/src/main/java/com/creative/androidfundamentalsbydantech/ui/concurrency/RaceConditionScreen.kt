package com.creative.androidfundamentalsbydantech.ui.concurrency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

private const val WORKERS = 20
private const val INCREMENTS_PER_WORKER = 5_000
private const val EXPECTED = WORKERS * INCREMENTS_PER_WORKER

@Composable
fun RaceConditionScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()

    DemoScaffold(title = "Race Condition", onBack = onBack) {
        SectionHeader("$WORKERS workers × $INCREMENTS_PER_WORKER = expected $EXPECTED")
        Explanation("So sánh 4 cách cộng dồn counter cùng lúc từ nhiều thread.")
        RaceDemo(scope)
    }
}

@Composable
private fun RaceDemo(scope: CoroutineScope) {
    var results by remember { mutableStateOf(emptyList<Pair<String, Int>>()) }
    var running by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(enabled = !running, onClick = {
                    running = true
                    scope.launch {
                        val raw = runRaw()
                        val atomic = runAtomic()
                        val sync = runSynchronized()
                        val mutex = runMutex()
                        results = listOf(
                            "raw var" to raw,
                            "AtomicInteger" to atomic,
                            "synchronized" to sync,
                            "Mutex" to mutex,
                        )
                        running = false
                    }
                }) { Text("Run") }
                if (running) Text("running…", modifier = Modifier.padding(start = 8.dp))
            }
            results.forEach { (label, v) ->
                val ok = v == EXPECTED
                Text(
                    "$label = $v  ${if (ok) "OK" else "LOST $EXPECTED - $v = ${EXPECTED - v}"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}

private suspend fun runRaw(): Int = withContext(Dispatchers.Default) {
    var counter = 0
    val jobs = List(WORKERS) {
        async {
            repeat(INCREMENTS_PER_WORKER) { counter++ }
        }
    }
    jobs.awaitAll()
    counter
}

private suspend fun runAtomic(): Int = withContext(Dispatchers.Default) {
    val counter = AtomicInteger(0)
    val jobs = List(WORKERS) {
        async {
            repeat(INCREMENTS_PER_WORKER) { counter.incrementAndGet() }
        }
    }
    jobs.awaitAll()
    counter.get()
}

private suspend fun runSynchronized(): Int = withContext(Dispatchers.Default) {
    val lock = Any()
    var counter = 0
    val jobs = List(WORKERS) {
        async {
            repeat(INCREMENTS_PER_WORKER) {
                synchronized(lock) { counter++ }
            }
        }
    }
    jobs.awaitAll()
    counter
}

private suspend fun runMutex(): Int = withContext(Dispatchers.Default) {
    val mutex = Mutex()
    var counter = 0
    val jobs = List(WORKERS) {
        async {
            repeat(INCREMENTS_PER_WORKER) {
                mutex.withLock { counter++ }
            }
        }
    }
    jobs.awaitAll()
    counter
}
