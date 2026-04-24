package com.creative.androidfundamentalsbydantech.ui.arch.mvi

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creative.androidfundamentalsbydantech.ui.arch.common.PatternScreen

@Composable
fun MviShowcaseScreen(onBack: () -> Unit, vm: SimpleMviViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()
    val input = remember { mutableStateOf("") }

    // PatternScreen takes care of the common UI; we inject pattern-specific
    // notes via tradeoffs and render an optional error banner above it.
    if (state.lastError != null) {
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Text(
                "error: ${state.lastError}",
                modifier = Modifier.padding(12.dp),
                color = MaterialTheme.colorScheme.error,
            )
            TextButton(onClick = { vm.onIntent(MviIntent.DismissError) }) { Text("Dismiss") }
        }
    }

    PatternScreen(
        title = if (state.busy) "MVI (busy…)" else "MVI",
        tagline = "Unidirectional: Intent -> Event -> Reducer -> State. " +
            "State immutable; reducer là pure function; side-effect để trong store.",
        input = input,
        history = state.history,
        onBack = onBack,
        onEncode = { vm.onIntent(MviIntent.Encode(input.value)) },
        onClear = { vm.onIntent(MviIntent.Clear) },
        tradeoffs = listOf(
            "Kỷ luật cao: State single-source-of-truth; dễ time-travel debug.",
            "Tách pure reducer từ side-effect -> test reducer trivially.",
            "Verbose cho feature đơn giản; mỗi thay đổi cần một Intent + Event.",
        ),
    )
}
