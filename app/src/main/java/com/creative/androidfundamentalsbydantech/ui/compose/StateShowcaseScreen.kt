package com.creative.androidfundamentalsbydantech.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import com.creative.androidfundamentalsbydantech.ui.learning.LearningContent
import com.creative.androidfundamentalsbydantech.ui.learning.LessonScaffold

val LocalAccent = compositionLocalOf { "default-accent" }

@Composable
fun StateShowcaseScreen(
    onBack: () -> Unit,
    completed: Boolean = false,
    onComplete: (() -> Unit)? = null,
    onOpenLab: (() -> Unit)? = null,
) {
    val lesson = requireNotNull(LearningContent.lessonById(LearningContent.COMPOSE_STATE))

    LessonScaffold(
        lesson = lesson,
        completed = completed,
        onBack = onBack,
        onComplete = onComplete,
        onOpenLab = onOpenLab,
    ) {
        SectionHeader("remember vs rememberSaveable")
        Explanation(
            "remember survives recomposition, rememberSaveable also survives " +
                "process death / config change. Xoay màn hình để thấy sự khác biệt.",
        )
        CounterRow(label = "remember", useSaveable = false)
        CounterRow(label = "rememberSaveable", useSaveable = true)

        SectionHeader("derivedStateOf")
        Explanation(
            "Chỉ recompute khi giá trị phái sinh thực sự đổi — dùng cho 'show scroll-to-top'.",
        )
        ScrollDerivedDemo()

        SectionHeader("State Hoisting")
        Explanation(
            "Stateful wrapper giữ state bên trong; stateless child chỉ nhận value + onChange.",
        )
        var hoisted by rememberSaveable { mutableStateOf("") }
        HoistedEditor(value = hoisted, onValueChange = { hoisted = it })

        SectionHeader("CompositionLocal")
        Explanation(
            "Truyền dữ liệu ngầm xuống cây Compose mà không cần prop-drill.",
        )
        CompositionLocalProvider(LocalAccent provides "amber-300") {
            NestedConsumer()
        }
    }
}

@Composable
private fun CounterRow(label: String, useSaveable: Boolean) {
    var count by if (useSaveable) {
        rememberSaveable { mutableIntStateOf(0) }
    } else {
        remember { mutableIntStateOf(0) }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("$label = $count", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { count++ }) { Text("+1") }
        }
    }
}

@Composable
private fun ScrollDerivedDemo() {
    val state = rememberLazyListState()
    val showScrollToTop by remember {
        derivedStateOf { state.firstVisibleItemIndex > 3 }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        LazyColumn(state = state, modifier = Modifier.height(180.dp).padding(8.dp)) {
            items(count = 50) { idx ->
                Text("Row #$idx", modifier = Modifier.padding(8.dp))
            }
        }
    }
    Text(
        text = if (showScrollToTop) "[scroll past 3 -> show FAB]" else "[top area -> hide FAB]",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(4.dp),
    )
}

@Composable
private fun HoistedEditor(value: String, onValueChange: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text("Hoisted = \"$value\"", modifier = Modifier.padding(end = 8.dp))
            Button(onClick = { onValueChange(value + "a") }) { Text("append 'a'") }
        }
    }
}

@Composable
private fun NestedConsumer() {
    val accent = LocalAccent.current
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            "Nested sees accent = $accent",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(12.dp),
        )
    }
}
