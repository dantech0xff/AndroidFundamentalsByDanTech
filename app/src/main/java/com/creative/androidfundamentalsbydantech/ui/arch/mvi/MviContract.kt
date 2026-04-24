package com.creative.androidfundamentalsbydantech.ui.arch.mvi

import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry

/** Single source of truth state — immutable. */
data class MviState(
    val history: List<ArchHistoryEntry> = emptyList(),
    val busy: Boolean = false,
    val lastError: String? = null,
)

/** User-facing intentions. */
sealed interface MviIntent {
    data class Encode(val input: String) : MviIntent
    data object Clear : MviIntent
    data object DismissError : MviIntent
}

/** Pure reducer: given current state + intent, produce next state.
 *  Side-effects (Base64 CPU work) are handled by the store, not here. */
fun reduce(state: MviState, event: MviEvent): MviState = when (event) {
    MviEvent.StartBusy -> state.copy(busy = true, lastError = null)
    is MviEvent.Appended -> state.copy(
        history = listOf(event.entry) + state.history,
        busy = false,
    )
    is MviEvent.Failed -> state.copy(busy = false, lastError = event.message)
    MviEvent.Cleared -> state.copy(history = emptyList())
    MviEvent.DismissedError -> state.copy(lastError = null)
}

/** Internal state-change events — dispatched from the store. */
sealed interface MviEvent {
    data object StartBusy : MviEvent
    data class Appended(val entry: ArchHistoryEntry) : MviEvent
    data class Failed(val message: String) : MviEvent
    data object Cleared : MviEvent
    data object DismissedError : MviEvent
}
