package com.creative.androidfundamentalsbydantech.ui.arch.mvc

import android.os.Handler
import android.os.Looper
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * MVC Controller — orchestrates: receives intent from View, asks Model to
 * change, then calls render back on the main thread so View can redraw. The
 * Controller has no reference to a concrete View type — just a render callback.
 */
class SimpleMvcController(
    private val model: SimpleMvcModel,
    private val render: (List<ArchHistoryEntry>) -> Unit,
) {
    private val worker = Executors.newSingleThreadExecutor()
    private val main = Handler(Looper.getMainLooper())
    private val disposed = AtomicBoolean(false)

    fun onEncodeClicked(input: String) {
        worker.submit {
            val encoded = TextTransform.encode(input)
            model.add(ArchHistoryEntry(plain = input, encoded = encoded))
            renderOnMain(model.history)
        }
    }

    fun onClearClicked() {
        worker.submit {
            model.clear()
            renderOnMain(model.history)
        }
    }

    fun dispose() {
        disposed.set(true)
        worker.shutdownNow()
        main.removeCallbacksAndMessages(null)
    }

    private fun renderOnMain(snapshot: List<ArchHistoryEntry>) {
        if (disposed.get()) return
        main.post {
            if (!disposed.get()) render(snapshot)
        }
    }
}
