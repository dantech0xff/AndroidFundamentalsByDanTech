package com.creative.androidfundamentalsbydantech.ui.interop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.creative.androidfundamentalsbydantech.ui.common.DemoScaffold
import com.creative.androidfundamentalsbydantech.ui.common.Explanation
import com.creative.androidfundamentalsbydantech.ui.common.SectionHeader
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun AndroidViewInteropScreen(onBack: () -> Unit) {
    DemoScaffold(title = "AndroidView Interop", onBack = onBack) {
        SectionHeader("Custom View inside Compose")
        Explanation(
            "Dùng AndroidView để nhúng View truyền thống (vd custom View override onDraw). " +
                "factory tạo View; update chạy khi state Compose thay đổi.",
        )
        var amplitude by remember { mutableFloatStateOf(0.4f) }

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            AndroidView(
                factory = { ctx -> WaveView(ctx).apply { setWaveAmplitude(amplitude) } },
                update = { view -> view.setWaveAmplitude(amplitude) },
                modifier = Modifier.fillMaxWidth().height(180.dp),
            )
        }
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            androidx.compose.foundation.layout.Row(modifier = Modifier.padding(12.dp)) {
                Text(
                    "amplitude = ${"%.2f".format(amplitude)}",
                    modifier = Modifier.padding(end = 12.dp),
                )
                Button(onClick = { amplitude = (amplitude + 0.1f).coerceAtMost(1f) }) { Text("+") }
                Button(
                    onClick = { amplitude = (amplitude - 0.1f).coerceAtLeast(0.1f) },
                    modifier = Modifier.padding(start = 8.dp),
                ) { Text("-") }
            }
        }
    }
}

class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF6650A4")
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }
    private val path = Path()
    private var amplitude = 0.4f
    private var phase = 0f

    fun setWaveAmplitude(value: Float) {
        amplitude = value.coerceIn(0.05f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val mid = h / 2f
        path.reset()
        path.moveTo(0f, mid)
        var x = 0f
        val step = w / 80f
        while (x <= w) {
            val y = mid + sin(x / w * 4 * PI.toFloat() + phase) * (h * 0.4f) * amplitude
            path.lineTo(x, y)
            x += step
        }
        canvas.drawPath(path, paint)
        phase += 0.08f
        postInvalidateOnAnimation()
    }
}
