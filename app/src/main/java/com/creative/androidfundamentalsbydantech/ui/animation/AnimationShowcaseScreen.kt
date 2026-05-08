package com.creative.androidfundamentalsbydantech.ui.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AnimationShowcaseScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Animation", onBack = onBack) {
        SectionHeader("animate*AsState")
        Explanation("Value animation tự động chạy khi state đổi.")
        AnimateAsStateDemo()

        SectionHeader("AnimatedVisibility")
        Explanation("Enter / exit transitions cho composable hiện / ẩn.")
        AnimatedVisibilityDemo()

        SectionHeader("AnimatedContent")
        Explanation("Chuyển đổi giữa nhiều nội dung với transition tuỳ biến.")
        AnimatedContentDemo()

        SectionHeader("Crossfade")
        Explanation("Fade giữa các UI state đơn giản.")
        CrossfadeDemo()

        SectionHeader("updateTransition (multi-prop)")
        Explanation("Một transition lái nhiều thuộc tính cùng lúc theo state.")
        UpdateTransitionDemo()

        SectionHeader("InfiniteTransition")
        Explanation("Loop vô hạn — pulse, loading, shimmer.")
        InfiniteTransitionDemo()

        SectionHeader("Spring / Tween / Keyframes")
        Explanation("Ba loại animation spec phổ biến.")
        SpecsDemo()

        SectionHeader("Gesture-driven Animatable")
        Explanation("Kéo thả + snapback dùng Animatable.")
        GestureAnimatableDemo()
    }
}

@Composable
private fun AnimateAsStateDemo() {
    var toggled by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (toggled) Color(0xFF6650A4) else Color(0xFFEFB8C8),
        label = "color",
    )
    val size by animateDpAsState(targetValue = if (toggled) 96.dp else 48.dp, label = "size")
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(modifier = Modifier.size(size).clip(RoundedCornerShape(12.dp)).background(color))
            Button(onClick = { toggled = !toggled }) { Text("Toggle") }
        }
    }
}

@Composable
private fun AnimatedVisibilityDemo() {
    var visible by remember { mutableStateOf(true) }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Button(onClick = { visible = !visible }) { Text(if (visible) "Hide" else "Show") }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInHorizontally { -it },
                exit = fadeOut() + slideOutHorizontally { -it },
            ) {
                Text(
                    "Hello slide-fade!",
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
private fun AnimatedContentDemo() {
    var idx by remember { mutableIntStateOf(0) }
    val pages = listOf("Page A", "Page B", "Page C")
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Button(onClick = { idx = (idx + 1) % pages.size }) { Text("Next") }
            AnimatedContent(targetState = idx, label = "page") { state ->
                Text(
                    pages[state],
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}

@Composable
private fun CrossfadeDemo() {
    var tab by remember { mutableStateOf("A") }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { tab = "A" }) { Text("A") }
                Button(onClick = { tab = "B" }) { Text("B") }
            }
            Crossfade(targetState = tab, label = "tab") { current ->
                Text(
                    "Showing $current",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}

private enum class BoxState { Small, Big }

@Composable
private fun UpdateTransitionDemo() {
    var state by remember { mutableStateOf(BoxState.Small) }
    val transition = updateTransition(targetState = state, label = "box")
    val size by transition.animateDp(
        transitionSpec = { spring() },
        label = "size",
    ) { if (it == BoxState.Big) 120.dp else 48.dp }
    val color by transition.animateColor(
        transitionSpec = { tween(400) },
        label = "color",
    ) {
        if (it == BoxState.Big) Color(0xFF03DAC5) else Color(0xFFBB86FC)
    }
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(Modifier.size(size).clip(RoundedCornerShape(12.dp)).background(color))
            Button(onClick = {
                state = if (state == BoxState.Small) BoxState.Big else BoxState.Small
            }) { Text("Swap") }
        }
    }
}

@Composable
private fun InfiniteTransitionDemo() {
    val transition = rememberInfiniteTransition(label = "pulse")
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "alpha",
    )
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color(0xFF6200EE).copy(alpha = alpha)),
            )
            Text("pulsing", modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Composable
private fun SpecsDemo() {
    var toggle by remember { mutableStateOf(false) }
    val springed by animateDpAsState(
        targetValue = if (toggle) 160.dp else 48.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "spring",
    )
    val tweened by animateDpAsState(
        targetValue = if (toggle) 160.dp else 48.dp,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "tween",
    )
    val keyed by animateDpAsState(
        targetValue = if (toggle) 160.dp else 48.dp,
        animationSpec = keyframes {
            durationMillis = 900
            48.dp at 0
            200.dp at 450
            160.dp at 900
        },
        label = "keyframes",
    )
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Bar(label = "spring", barWidth = springed)
            Bar(label = "tween", barWidth = tweened)
            Bar(label = "keyframes", barWidth = keyed)
            Button(onClick = { toggle = !toggle }) { Text("Toggle") }
        }
    }
}

@Composable
private fun Bar(label: String, barWidth: Dp) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.padding(end = 8.dp))
        Box(
            modifier = Modifier
                .height(18.dp)
                .width(barWidth)
                .clip(RoundedCornerShape(9.dp))
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}

@Composable
private fun GestureAnimatableDemo() {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                    .size(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFE91E63))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, drag ->
                                scope.launch { offsetX.snapTo(offsetX.value + drag.x) }
                                scope.launch { offsetY.snapTo(offsetY.value + drag.y) }
                            },
                            onDragEnd = {
                                scope.launch { offsetX.animateTo(0f, spring()) }
                                scope.launch { offsetY.animateTo(0f, spring()) }
                            },
                        )
                    },
            )
            Text(
                "Drag me, release to snap back",
                modifier = Modifier.align(Alignment.BottomStart),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
