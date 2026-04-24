package com.creative.androidfundamentalsbydantech.ui.arch.common

import android.util.Base64

/** Tiny shared demo transform so every pattern encodes the same way. */
object TextTransform {
    fun encode(plain: String): String =
        if (plain.isEmpty()) "" else Base64.encodeToString(plain.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

data class ArchHistoryEntry(val plain: String, val encoded: String)
