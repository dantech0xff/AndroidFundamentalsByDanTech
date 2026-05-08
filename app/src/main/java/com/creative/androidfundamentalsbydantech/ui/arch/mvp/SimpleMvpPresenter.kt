package com.creative.androidfundamentalsbydantech.ui.arch.mvp

import android.os.Handler
import android.os.Looper
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class SimpleMvpPresenter(
    private val model: SimpleMvpModel,
) : MvpContract.Presenter {

    private var view: MvpContract.View? = null
    private val worker = Executors.newSingleThreadExecutor()
    private val main = Handler(Looper.getMainLooper())
    private val detached = AtomicBoolean(false)

    override fun attach(view: MvpContract.View) {
        detached.set(false)
        this.view = view
        view.showHistory(model.history)
    }

    override fun detach() {
        detached.set(true)
        view = null
        main.removeCallbacksAndMessages(null)
        worker.shutdownNow()
    }

    override fun onEncode(input: String) {
        showBusyOnMain(true)
        worker.submit {
            val encoded = TextTransform.encode(input)
            model.add(ArchHistoryEntry(plain = input, encoded = encoded))
            showSnapshotOnMain(model.history)
        }
    }

    override fun onClear() {
        worker.submit {
            model.clear()
            showSnapshotOnMain(model.history)
        }
    }

    private fun showSnapshotOnMain(snapshot: List<ArchHistoryEntry>) {
        if (detached.get()) return
        main.post {
            if (!detached.get()) {
                view?.showBusy(false)
                view?.showHistory(snapshot)
            }
        }
    }

    private fun showBusyOnMain(isBusy: Boolean) {
        if (detached.get()) return
        main.post {
            if (!detached.get()) view?.showBusy(isBusy)
        }
    }
}
