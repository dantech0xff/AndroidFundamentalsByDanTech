package com.creative.androidfundamentalsbydantech.ui.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun CanvasPlaygroundScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Canvas Playground", onBack = onBack) {
        SectionHeader("Shapes + Gradients")
        Explanation("drawCircle, drawRect, Path, linearGradient, radialGradient.")
        ShapesAndGradients()

        SectionHeader("Finger Paint")
        Explanation("Bắt điểm kéo bằng pointerInput rồi drawPath.")
        FingerPaint()

        SectionHeader("Particles (withFrameNanos)")
        Explanation("Vòng lặp animation chạy mỗi frame — đếm delta theo nanoseconds.")
        ParticleField()
    }
}

@Composable
private fun ShapesAndGradients() {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(12.dp),
        ) {
            val w = size.width
            val h = size.height
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Yellow, Color.Transparent),
                    center = Offset(w * 0.25f, h * 0.5f),
                    radius = h * 0.4f,
                ),
                radius = h * 0.4f,
                center = Offset(w * 0.25f, h * 0.5f),
            )
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF03DAC5), Color(0xFF6200EE)),
                    start = Offset.Zero,
                    end = Offset(w, h),
                ),
                topLeft = Offset(w * 0.45f, h * 0.15f),
                size = Size(w * 0.45f, h * 0.35f),
            )
            val path = Path().apply {
                moveTo(w * 0.45f, h * 0.9f)
                lineTo(w * 0.7f, h * 0.55f)
                lineTo(w * 0.95f, h * 0.9f)
                close()
            }
            drawPath(path, color = Color(0xFFFF5722))
        }
    }
}

@Composable
private fun FingerPaint() {
    val completed = remember { mutableStateListOf<Path>() }
    var inProgress by remember { mutableStateOf<Path?>(null) }
    // Bump to force Canvas recomposition while dragging (Path mutation doesn't trigger snapshot invalidation).
    var drawTick by remember { mutableIntStateOf(0) }

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(onClick = {
                    completed.clear()
                    inProgress = null
                    drawTick++
                }) { Text("Clear") }
                Text(
                    "strokes: ${completed.size}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(0xFFF5F5F5))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                inProgress = Path().apply { moveTo(offset.x, offset.y) }
                                drawTick++
                            },
                            onDrag = { change, _ ->
                                inProgress?.lineTo(change.position.x, change.position.y)
                                drawTick++
                            },
                            onDragEnd = {
                                inProgress?.let { completed.add(it) }
                                inProgress = null
                                drawTick++
                            },
                        )
                    },
            ) {
                @Suppress("UNUSED_EXPRESSION") drawTick // observe so recomposition invalidates draw
                completed.forEach { p ->
                    drawPath(path = p, color = Color(0xFF6200EE), style = Stroke(width = 6f))
                }
                inProgress?.let { p ->
                    drawPath(path = p, color = Color(0xFFE91E63), style = Stroke(width = 6f))
                }
            }
        }
    }
}

private data class Particle(var x: Float, var y: Float, var vx: Float, var vy: Float)

@Composable
private fun ParticleField() {
    val particles = remember {
        List(24) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                vx = (Random.nextFloat() - 0.5f) * 0.4f,
                vy = (Random.nextFloat() - 0.5f) * 0.4f,
            )
        }
    }
    var lastNanos by remember { mutableLongStateOf(0L) }
    var phase by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { now ->
                val dt = if (lastNanos == 0L) 0f else (now - lastNanos) / 1_000_000_000f
                lastNanos = now
                phase += dt
                particles.forEach { p ->
                    p.x = (p.x + p.vx * dt + 1f) % 1f
                    p.y = (p.y + p.vy * dt + 1f) % 1f
                }
            }
        }
    }

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            Canvas(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                val w = size.width
                val h = size.height
                val radius = 6f + 4f * (0.5f + 0.5f * sin(phase * 2 * PI.toFloat()))
                particles.forEach { p ->
                    drawCircle(
                        color = Color(0xFF03DAC5).copy(alpha = 0.6f),
                        radius = radius,
                        center = Offset(p.x * w, p.y * h),
                    )
                }
                val wavePath = Path()
                val step = w / 40f
                wavePath.moveTo(0f, h - 10f)
                var x = 0f
                while (x <= w) {
                    val y = h - 10f + 6f * sin(x / w * 4 * PI.toFloat() + phase * 2)
                    wavePath.lineTo(x, y)
                    x += step
                }
                drawPath(wavePath, color = Color(0xFF6200EE), style = Stroke(width = 2f))
            }
        }
    }
}
