package com.creative.androidfundamentalsbydantech.ui.arch.mvp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.PatternScreen

@Composable
fun MvpShowcaseScreen(onBack: () -> Unit) {
    val history = remember { mutableStateListOf<ArchHistoryEntry>() }
    val busy = remember { mutableStateOf(false) }
    val input = remember { mutableStateOf("") }

    val presenter = remember {
        SimpleMvpPresenter(SimpleMvpModel())
    }

    // The Compose View implements the contract; Presenter pushes snapshots in.
    val view = remember {
        object : MvpContract.View {
            override fun showHistory(entries: List<ArchHistoryEntry>) {
                history.clear()
                history.addAll(entries)
            }

            override fun showBusy(isBusy: Boolean) {
                busy.value = isBusy
            }
        }
    }

    DisposableEffect(Unit) {
        presenter.attach(view)
        onDispose { presenter.detach() }
    }

    PatternScreen(
        title = if (busy.value) "MVP (busy…)" else "MVP",
        tagline = "View implements Contract.View, Presenter implements Contract.Presenter — " +
            "mỗi bên có interface rõ ràng để mock/test.",
        input = input.value,
        onInputChange = { input.value = it },
        history = history,
        onBack = onBack,
        onEncode = { presenter.onEncode(input.value) },
        onClear = { presenter.onClear() },
        encodeEnabled = !busy.value,
        clearEnabled = !busy.value && history.isNotEmpty(),
        tradeoffs = listOf(
            "Rõ ràng hợp đồng giữa View và Presenter — test Presenter dễ.",
            "Hai interface cho mỗi feature: nhiều boilerplate.",
            "Presenter giữ tham chiếu View -> phải attach/detach đúng lifecycle.",
        ),
    )
}
