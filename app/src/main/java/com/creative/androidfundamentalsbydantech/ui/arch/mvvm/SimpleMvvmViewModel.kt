package com.creative.androidfundamentalsbydantech.ui.arch.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MvvmState(
    val history: List<ArchHistoryEntry> = emptyList(),
    val busy: Boolean = false,
)

@HiltViewModel
class SimpleMvvmViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MvvmState())
    val state: StateFlow<MvvmState> = _state.asStateFlow()

    fun onEncode(input: String) {
        _state.update { it.copy(busy = true) }
        viewModelScope.launch {
            val encoded = withContext(Dispatchers.Default) { TextTransform.encode(input) }
            _state.update { current ->
                current.copy(
                    history = listOf(ArchHistoryEntry(input, encoded)) + current.history,
                    busy = false,
                )
            }
        }
    }

    fun onClear() {
        _state.update { it.copy(history = emptyList()) }
    }
}
