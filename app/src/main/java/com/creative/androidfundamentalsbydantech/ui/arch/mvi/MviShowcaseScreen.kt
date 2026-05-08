package com.creative.androidfundamentalsbydantech.ui.arch.mvi

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidfundamentalsbydantech.ui.arch.common.PatternScreen

@Composable
fun MviShowcaseScreen(onBack: () -> Unit, vm: SimpleMviViewModel = hiltViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    val input = remember { mutableStateOf("") }

    PatternScreen(
        title = if (state.busy) "MVI (busy…)" else "MVI",
        tagline = "Unidirectional: Intent -> Event -> Reducer -> State. " +
            "State immutable; reducer là pure function; side-effect để trong store.",
        input = input.value,
        onInputChange = { input.value = it },
        history = state.history,
        onBack = onBack,
        onEncode = { vm.onIntent(MviIntent.Encode(input.value)) },
        onClear = { vm.onIntent(MviIntent.Clear) },
        encodeEnabled = !state.busy,
        clearEnabled = !state.busy && state.history.isNotEmpty(),
        feedback = {
            state.lastError?.let { error ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(
                        "error: $error",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.error,
                    )
                    TextButton(onClick = { vm.onIntent(MviIntent.DismissError) }) { Text("Dismiss") }
                }
            }
        },
        tradeoffs = listOf(
            "Kỷ luật cao: State single-source-of-truth; dễ time-travel debug.",
            "Tách pure reducer từ side-effect -> test reducer trivially.",
            "Verbose cho feature đơn giản; mỗi thay đổi cần một Intent + Event.",
        ),
    )
}
