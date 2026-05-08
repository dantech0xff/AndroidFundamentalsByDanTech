package com.creative.androidfundamentalsbydantech.ui.concurrency

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
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

private class Account(var balance: Int = 1000) {
    fun transfer(other: Account, amount: Int) {
        synchronized(this) {
            Thread.sleep(50) // widen the race window
            synchronized(other) {
                this.balance -= amount
                other.balance += amount
            }
        }
    }
}

@Composable
fun DeadlockSimulatorScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Deadlock Simulator", onBack = onBack) {
        SectionHeader("Two threads, opposite lock order")
        Explanation(
            "Thread A khoá accountA trước rồi accountB; thread B ngược lại. Sớm muộn cả hai " +
                "sẽ cùng giữ một lock và chờ lock kia — deadlock. Quan sát Logcat tag \"Deadlock\".",
        )

        val accountA = remember { Account() }
        val accountB = remember { Account() }
        var running by remember { mutableStateOf(false) }
        var status by remember { mutableStateOf("idle") }

        val threadA = remember { HandlerThread("DL-ThreadA").apply { start() } }
        val threadB = remember { HandlerThread("DL-ThreadB").apply { start() } }
        val handlerA = remember { Handler(threadA.looper) }
        val handlerB = remember { Handler(threadB.looper) }

        DisposableEffect(Unit) {
            onDispose {
                threadA.quitSafely()
                threadB.quitSafely()
            }
        }

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = !running,
                    onClick = {
                        running = true
                        status = "running — check Logcat"
                        repeat(50) { i ->
                            handlerA.post {
                                Log.d("Deadlock", "A tries A->B #$i")
                                accountA.transfer(accountB, 1)
                                Log.d("Deadlock", "A done A->B #$i")
                            }
                            handlerB.post {
                                Log.d("Deadlock", "B tries B->A #$i")
                                accountB.transfer(accountA, 1)
                                Log.d("Deadlock", "B done B->A #$i")
                            }
                        }
                    },
                ) { Text("Simulate") }

                Text(status, style = MaterialTheme.typography.bodyLarge)
            }
        }

        SectionHeader("Fix")
        Explanation(
            "Luôn khoá theo thứ tự nhất quán (vd: sort theo hashCode) để không có chu trình chờ.",
        )
    }
}
