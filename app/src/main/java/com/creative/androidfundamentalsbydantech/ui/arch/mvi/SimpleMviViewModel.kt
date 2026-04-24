package com.creative.androidfundamentalsbydantech.ui.arch.mvi

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

@HiltViewModel
class SimpleMviViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MviState())
    val state: StateFlow<MviState> = _state.asStateFlow()

    fun onIntent(intent: MviIntent) {
        when (intent) {
            is MviIntent.Encode -> dispatchEncode(intent.input)
            MviIntent.Clear -> dispatch(MviEvent.Cleared)
            MviIntent.DismissError -> dispatch(MviEvent.DismissedError)
        }
    }

    private fun dispatchEncode(input: String) {
        dispatch(MviEvent.StartBusy)
        viewModelScope.launch {
            try {
                val encoded = withContext(Dispatchers.Default) { TextTransform.encode(input) }
                dispatch(MviEvent.Appended(ArchHistoryEntry(input, encoded)))
            } catch (t: Throwable) {
                dispatch(MviEvent.Failed(t.message ?: t::class.java.simpleName))
            }
        }
    }

    private fun dispatch(event: MviEvent) {
        _state.value = reduce(_state.value, event)
    }
}
