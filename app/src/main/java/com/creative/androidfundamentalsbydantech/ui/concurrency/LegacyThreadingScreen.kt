package com.creative.androidfundamentalsbydantech.ui.concurrency

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Composable
fun LegacyThreadingScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Legacy Threading", onBack = onBack) {
        SectionHeader("Thread")
        Explanation("Tạo OS thread thẳng — không share Looper, không nên dùng cho I/O nhiều.")
        RawThreadDemo()

        SectionHeader("Handler on Main Looper")
        Explanation("Post Runnable vào Main để cập nhật UI từ background.")
        MainHandlerDemo()

        SectionHeader("HandlerThread")
        Explanation("Background thread có Looper — xử lý message lần lượt.")
        HandlerThreadDemo()

        SectionHeader("ThreadPoolExecutor")
        Explanation("Pool tái sử dụng N worker thread; tiết kiệm tạo-huỷ thread.")
        ExecutorDemo()
    }
}

@Composable
private fun RawThreadDemo() {
    var status by remember { mutableStateOf("idle") }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                status = "thread starting…"
                Thread {
                    Thread.sleep(500)
                    val name = Thread.currentThread().name
                    mainHandler.post { status = "done on $name" }
                }.start()
            }) { Text("Run") }
            Text(status, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun MainHandlerDemo() {
    var tick by remember { mutableStateOf(0) }
    val handler = remember { Handler(Looper.getMainLooper()) }
    DisposableEffect(Unit) {
        onDispose { handler.removeCallbacksAndMessages(null) }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                handler.post { tick++ }
                handler.postDelayed({ tick += 10 }, 300)
            }) { Text("post + postDelayed") }
            Text("tick=$tick", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun HandlerThreadDemo() {
    val ht = remember {
        HandlerThread("demo-ht").apply { start() }
    }
    val workHandler = remember { Handler(ht.looper) }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    var status by remember { mutableStateOf("idle") }
    DisposableEffect(Unit) {
        onDispose { ht.quitSafely() }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                status = "queued"
                workHandler.post {
                    Thread.sleep(400)
                    val name = Thread.currentThread().name
                    mainHandler.post { status = "done on $name" }
                }
            }) { Text("Dispatch") }
            Text(status, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun ExecutorDemo() {
    val executor = remember { Executors.newFixedThreadPool(2) }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    var status by remember { mutableStateOf("idle") }
    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
            runCatching { executor.awaitTermination(1, TimeUnit.SECONDS) }
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                status = "submitted"
                executor.submit {
                    Thread.sleep(300)
                    val name = Thread.currentThread().name
                    mainHandler.post { status = "done on $name" }
                }
            }) { Text("Submit") }
            Text(status, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
