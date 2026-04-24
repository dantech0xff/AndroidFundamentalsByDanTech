package com.creative.androidfundamentalsbydantech.ui.arch.mvp

import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry

class SimpleMvpModel {
    private val _history = mutableListOf<ArchHistoryEntry>()
    val history: List<ArchHistoryEntry> get() = _history.toList()

    fun add(entry: ArchHistoryEntry) { _history.add(0, entry) }
    fun clear() { _history.clear() }
}
