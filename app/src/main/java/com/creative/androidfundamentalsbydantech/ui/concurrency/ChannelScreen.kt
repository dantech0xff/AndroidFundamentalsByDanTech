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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChannelScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()

    DemoScaffold(title = "Channel", onBack = onBack) {
        SectionHeader("Rendezvous Channel")
        Explanation("send suspend tới khi receive; 0 buffer.")
        RendezvousDemo(scope)

        SectionHeader("Buffered Channel")
        Explanation("Buffer 3 slot — send không block cho đến khi đầy.")
        BufferedDemo(scope)

        SectionHeader("produce{} builder")
        Explanation("Tạo ReceiveChannel từ coroutine.")
        ProduceDemo(scope)
    }
}

@Composable
private fun RendezvousDemo(scope: CoroutineScope) {
    var log by remember { mutableStateOf("—") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val channel = Channel<Int>(Channel.RENDEZVOUS)
                val events = mutableListOf<String>()
                scope.launch {
                    repeat(3) {
                        events += "send $it"
                        log = events.joinToString("\n")
                        channel.send(it)
                        events += "send $it done"
                        log = events.joinToString("\n")
                    }
                    channel.close()
                }
                scope.launch {
                    delay(400)
                    for (v in channel) {
                        events += "receive $v"
                        log = events.joinToString("\n")
                        delay(200)
                    }
                }
            }) { Text("Run") }
            Text(log, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun BufferedDemo(scope: CoroutineScope) {
    var log by remember { mutableStateOf("—") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val channel = Channel<Int>(capacity = 3)
                val events = mutableListOf<String>()
                scope.launch {
                    repeat(5) {
                        events += "send $it"
                        log = events.joinToString("\n")
                        channel.send(it)
                    }
                    channel.close()
                }
                scope.launch {
                    delay(500)
                    for (v in channel) {
                        events += "receive $v"
                        log = events.joinToString("\n")
                        delay(100)
                    }
                }
            }) { Text("Run") }
            Text(log, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ProduceDemo(scope: CoroutineScope) {
    var latest by remember { mutableStateOf(0) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val channel = scope.produce {
                    repeat(10) {
                        send(it)
                        delay(150)
                    }
                }
                scope.launch {
                    for (v in channel) latest = v
                }
            }) { Text("Start producer") }
            Text("latest=$latest", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
