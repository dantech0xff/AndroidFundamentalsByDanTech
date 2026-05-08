package com.creative.androidfundamentalsbydantech.ui.arch.mvc

import com.creative.androidfundamentalsbydantech.ui.arch.common.ArchHistoryEntry

/**
 * MVC Model — owns data, knows nothing about UI or platform.
 * Kept deliberately dumb: plain mutable list + mutation API.
 */
class SimpleMvcModel {
    private val _history = mutableListOf<ArchHistoryEntry>()
    val history: List<ArchHistoryEntry> get() = _history.toList()

    fun add(entry: ArchHistoryEntry) {
        _history.add(0, entry)
    }

    fun clear() {
        _history.clear()
    }
}
