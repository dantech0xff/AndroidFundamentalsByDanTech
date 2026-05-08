package com.creative.androidfundamentalsbydantech.ui.compose

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EffectsShowcaseScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Side-Effects", onBack = onBack) {
        SectionHeader("LaunchedEffect")
        Explanation("Chạy suspend khi enter Composition; restart khi key thay đổi.")
        LaunchedEffectDemo()

        SectionHeader("DisposableEffect")
        Explanation("Đăng ký + huỷ đăng ký khi composable rời khỏi cây.")
        DisposableEffectDemo()

        SectionHeader("SideEffect")
        Explanation("Gọi mỗi lần recomposition thành công — để sync state ra 'ngoài Compose'.")
        SideEffectDemo()

        SectionHeader("produceState")
        Explanation("Chuyển non-Compose state (VD: Flow, callback) thành Compose State<T>.")
        ProduceStateDemo()

        SectionHeader("rememberUpdatedState")
        Explanation(
            "Capture giá trị mới nhất của lambda bên trong LaunchedEffect dài hạn mà " +
                "không restart effect.",
        )
        RememberUpdatedStateDemo()

        SectionHeader("snapshotFlow")
        Explanation("Biến Compose state thành Flow để dùng operator Flow.")
        SnapshotFlowDemo()

        SectionHeader("rememberCoroutineScope")
        Explanation("Scope gắn với lifecycle của composable; launch từ event handler.")
        RememberCoroutineScopeDemo()
    }
}

@Composable
private fun LaunchedEffectDemo() {
    var key by remember { mutableIntStateOf(0) }
    var tickLog by remember { mutableStateOf("") }
    LaunchedEffect(key) {
        tickLog = "start key=$key"
        var i = 0
        while (true) {
            delay(500)
            i++
            tickLog = "key=$key tick=$i"
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(tickLog, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(end = 8.dp))
            Button(onClick = { key++ }) { Text("Rekey") }
        }
    }
}

@Composable
private fun DisposableEffectDemo() {
    var on by remember { mutableStateOf(true) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Button(onClick = { on = !on }) { Text(if (on) "Mount" else "Unmount") }
        }
    }
    if (on) {
        DisposableEffect(Unit) {
            android.util.Log.d("Effects", "DisposableEffect: register")
            onDispose { android.util.Log.d("Effects", "DisposableEffect: dispose") }
        }
        Text("[Xem Logcat tag Effects]", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun SideEffectDemo() {
    var value by remember { mutableIntStateOf(0) }
    SideEffect {
        android.util.Log.d("Effects", "SideEffect: value=$value")
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("value = $value")
            Button(onClick = { value++ }) { Text("Recompose") }
        }
    }
}

@Composable
private fun ProduceStateDemo() {
    val now: State<String> = produceState(initialValue = "-") {
        while (true) {
            value = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.US).format(java.util.Date())
            delay(1000)
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text("Now = ${now.value}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(12.dp))
    }
}

@Composable
private fun RememberUpdatedStateDemo() {
    var message by remember { mutableStateOf("initial") }
    val latestMessage by rememberUpdatedState(message)
    var delayedEcho by remember { mutableStateOf("-") }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            delayedEcho = "After 2s snapshot: $latestMessage"
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(delayedEcho, modifier = Modifier.padding(end = 8.dp))
            Button(onClick = { message = "msg-${System.currentTimeMillis() % 1000}" }) {
                Text("Change msg")
            }
        }
    }
}

@Composable
private fun SnapshotFlowDemo() {
    var counter by remember { mutableIntStateOf(0) }
    var doubled by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        snapshotFlow { counter }.collectLatest { c ->
            delay(300)
            doubled = c * 2
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("counter=$counter doubled=$doubled")
            Button(onClick = { counter++ }) { Text("+1") }
        }
    }
}

@Composable
private fun RememberCoroutineScopeDemo() {
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("idle") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(status)
            Button(onClick = {
                scope.launch {
                    status = "working"
                    delay(1000)
                    status = "done ${System.currentTimeMillis() % 1000}"
                }
            }) { Text("Run") }
        }
    }
}
