package com.creative.androidfundamentalsbydantech.ui.arch.mvp

import android.os.Handler
import android.os.Looper
import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry
import com.creative.androidfundamentalsbydantech.ui.arch.common.TextTransform
import java.util.concurrent.Executors

class SimpleMvpPresenter(
    private val model: SimpleMvpModel,
) : MvpContract.Presenter {

    private var view: MvpContract.View? = null
    private val worker = Executors.newSingleThreadExecutor()
    private val main = Handler(Looper.getMainLooper())

    override fun attach(view: MvpContract.View) {
        this.view = view
        view.showHistory(model.history)
    }

    override fun detach() {
        this.view = null
        worker.shutdownNow()
    }

    override fun onEncode(input: String) {
        view?.showBusy(true)
        worker.submit {
            val encoded = TextTransform.encode(input)
            model.add(ArchHistoryEntry(plain = input, encoded = encoded))
            val snapshot = model.history
            main.post {
                view?.showBusy(false)
                view?.showHistory(snapshot)
            }
        }
    }

    override fun onClear() {
        model.clear()
        view?.showHistory(model.history)
    }
}
