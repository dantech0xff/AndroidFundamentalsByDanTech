package com.creative.androidfundamentalsbydantech.ui.arch.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class MvvmState(
    val history: List<ArchHistoryEntry> = emptyList(),
    val busy: Boolean = false,
)

@HiltViewModel
class SimpleMvvmViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MvvmState())
    val state: StateFlow<MvvmState> = _state.asStateFlow()

    fun onEncode(input: String) {
        _state.value = _state.value.copy(busy = true)
        viewModelScope.launch {
            val encoded = withContext(Dispatchers.Default) { TextTransform.encode(input) }
            _state.value = _state.value.copy(
                history = listOf(ArchHistoryEntry(input, encoded)) + _state.value.history,
                busy = false,
            )
        }
    }

    fun onClear() {
        _state.value = _state.value.copy(history = emptyList())
    }
}
