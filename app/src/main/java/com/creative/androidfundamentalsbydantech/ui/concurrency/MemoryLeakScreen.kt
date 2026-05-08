package com.creative.androidfundamentalsbydantech.ui.concurrency

import android.content.Context
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader

@Composable
fun MemoryLeakScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var leaksFired by remember { mutableStateOf(0) }

    DemoScaffold(title = "Memory Leak", onBack = onBack) {
        SectionHeader("Intentional Activity leak")
        Explanation(
            "Giữ Activity context trong singleton Thread chạy vô hạn — Activity " +
                "không GC được khi bạn quay lại. Debug build có LeakCanary đã cấu hình " +
                "sẽ tự báo trong ~30s sau khi bạn rời khỏi màn.",
        )

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    LeakSimulator.start(context)
                    leaksFired++
                }) { Text("Leak this Activity") }
                Text("fired=$leaksFired", style = MaterialTheme.typography.bodyLarge)
            }
        }

        SectionHeader("Cách fix")
        Explanation(
            "1) Không giữ Context của Activity trong static / long-lived thread.\n" +
                "2) Dùng applicationContext nếu cần Context dài hạn.\n" +
                "3) Đặt listener/callback trong onStart → remove trong onStop.\n" +
                "4) Với Coroutine, scope theo viewModelScope / lifecycleScope.",
        )
    }
}

/** Cố tình giữ Context trong singleton — sẽ bị LeakCanary phát hiện. */
private object LeakSimulator {
    private val leakedContexts = mutableListOf<Context>()
    private var started = false

    fun start(context: Context) {
        leakedContexts += context
        if (started) return
        started = true
        Thread {
            while (true) {
                try {
                    Thread.sleep(1_000)
                } catch (_: InterruptedException) {
                    return@Thread
                }
            }
        }.apply {
            isDaemon = true
            name = "leak-simulator"
        }.start()
    }
}
