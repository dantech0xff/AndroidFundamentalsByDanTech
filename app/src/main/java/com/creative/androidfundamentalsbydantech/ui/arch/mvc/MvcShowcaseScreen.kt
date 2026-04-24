package com.creative.androidfundamentalsbydantech.ui.arch.mvc

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.PatternScreen

@Composable
fun MvcShowcaseScreen(onBack: () -> Unit) {
    val history = remember { mutableStateListOf<ArchHistoryEntry>() }
    val model = remember { SimpleMvcModel() }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    val input = remember { mutableStateOf("") }

    val controller = remember {
        SimpleMvcController(model) { latest ->
            mainHandler.post {
                history.clear()
                history.addAll(latest)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { controller.dispose() }
    }

    PatternScreen(
        title = "MVC",
        tagline = "Controller xử lý logic + thread, ra lệnh cho Model, render ngược lại View.",
        input = input,
        history = history,
        onBack = onBack,
        onEncode = { controller.onEncodeClicked(input.value) },
        onClear = { controller.onClearClicked() },
        tradeoffs = listOf(
            "Đơn giản, dễ bắt đầu.",
            "Controller dễ phình to; khó test vì bám vào render callback.",
            "Không có data stream — View phụ thuộc Controller gọi lại đúng thread.",
        ),
    )
}
