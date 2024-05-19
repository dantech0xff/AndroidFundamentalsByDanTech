package com.creative.androidfundamentalsbydantech.fragment.mvp

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPModel {
    private val listEncrypted: MutableMap<String, String> = mutableMapOf()

    fun addEncryptedData(data: String, encryptedData: String) {
        listEncrypted[data] = encryptedData
    }

    fun clearEncryptedData() {
        listEncrypted.clear()
    }

    fun getListEncryptedData(): String {
        if (listEncrypted.isEmpty()) return ""
        return listEncrypted.map {
            "${it.key} -> ${it.value}"
        }.joinToString("\n")
    }

    fun getListEncrypted(): Map<String, String> = listEncrypted

    fun getSecretKey(): String = "s1mple-secr3t-xx"
}