package com.creative.androidfundamentalsbydantech.ui.arch.clean.domain

/** Pure domain model — no Android, no framework. */
data class WishlistItem(
    val id: Long,
    val label: String,
    val priority: Int, // 1 = top
)
