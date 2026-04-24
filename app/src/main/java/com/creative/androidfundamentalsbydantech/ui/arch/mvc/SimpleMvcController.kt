package com.creative.androidfundamentalsbydantech.ui.arch.mvc

import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import java.util.concurrent.Executors

/**
 * MVC Controller — orchestrates: receives intent from View, asks Model
 * to change, calls render lambda back on the Main thread so View can redraw.
 * The Controller has no reference to concrete View type — just a render callback.
 */
class SimpleMvcController(
    private val model: SimpleMvcModel,
    private val render: (List<ArchHistoryEntry>) -> Unit,
) {
    private val worker = Executors.newSingleThreadExecutor()

    fun onEncodeClicked(input: String) {
        worker.submit {
            val encoded = TextTransform.encode(input)
            model.add(ArchHistoryEntry(plain = input, encoded = encoded))
            render(model.history)
        }
    }

    fun onClearClicked() {
        model.clear()
        render(model.history)
    }

    fun dispose() {
        worker.shutdownNow()
    }
}
