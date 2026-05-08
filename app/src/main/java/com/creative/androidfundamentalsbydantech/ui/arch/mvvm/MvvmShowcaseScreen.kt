package com.creative.androidfundamentalsbydantech.ui.arch.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidfundamentalsbydantech.ui.arch.common.PatternScreen

@Composable
fun MvvmShowcaseScreen(onBack: () -> Unit, vm: SimpleMvvmViewModel = hiltViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    val input = remember { mutableStateOf("") }

    PatternScreen(
        title = if (state.busy) "MVVM (busy…)" else "MVVM",
        tagline = "ViewModel giữ StateFlow; View không giữ reference ngược lại. " +
            "ViewModel sống qua config change, dễ test, chạy trên viewModelScope.",
        input = input.value,
        onInputChange = { input.value = it },
        history = state.history,
        onBack = onBack,
        onEncode = { vm.onEncode(input.value) },
        onClear = { vm.onClear() },
        encodeEnabled = !state.busy,
        clearEnabled = !state.busy && state.history.isNotEmpty(),
        tradeoffs = listOf(
            "Tự động sống qua config change; viewModelScope hủy khi ViewModel cleared.",
            "StateFlow đơn hướng — View chỉ observe, không biết implementation.",
            "Thiếu structure cho side-effect một lần (navigate, snackbar) — SharedFlow bù.",
        ),
    )
}
