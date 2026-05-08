package com.creative.androidfundamentalsbydantech.ui.arch.mvp

import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry

/**
 * MVP Contract — both sides of the boundary expressed as interfaces so
 * Presenter can be unit-tested against a fake View.
 */
interface MvpContract {
    interface View {
        fun showHistory(entries: List<ArchHistoryEntry>)
        fun showBusy(busy: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun onEncode(input: String)
        fun onClear()
    }
}
