package com.creative.androidfundamentalsbydantech.fragment.mvc

import android.os.Handler
import android.os.Looper
import android.util.Base64
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVCController(
    private val view: SimpleMVCFragment,
    private val model: SimpleMVCModel
) {
    private val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val executor by lazy {
        ThreadPoolExecutor(
            1, 1,
            3, TimeUnit.SECONDS, LinkedBlockingDeque()
        )
    }

    fun getSnapshotData(): String {
        val listEncrypted = model.getListEncrypted()
        val jsonObject = JSONObject()
        listEncrypted.forEach {
            jsonObject.put(it.key, it.value)
        }
        return jsonObject.toString()
    }

    fun restoreSnapshotData(snapshot: String) {
        try {
            val jsonObject = JSONObject(snapshot)
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                model.addEncryptedData(key, jsonObject.getString(key))
            }
            mainHandler.post {
                view.updateEncryptedText(model.getListEncryptedData())
            }
        } catch (e: Exception) {
            model.clearEncryptedData()
            mainHandler.post {
                view.showMessage("Failed to restore data!")
                view.updateEncryptedText("")
            }
        }
    }

    fun encryptData(data: String) {
        if (data.isNotEmpty()) {
            executor.execute {
                val encrypted = internalEncryptData(data)
                model.addEncryptedData(data, encrypted)
                mainHandler.post {
                    view.updateEncryptedText(model.getListEncryptedData())
                    view.clearInputText()
                }
            }
        } else {
            mainHandler.post {
                view.showMessage("Please input data")
            }
        }
    }

    private fun internalEncryptData(data: String): String {
        val bytes = Base64.encode(data.toByteArray(), Base64.DEFAULT)
        return String(bytes)
    }

    fun clearData() {
        executor.execute {
            model.clearEncryptedData()
            mainHandler.post {
                view.updateEncryptedText(model.getListEncryptedData())
                view.clearInputText()
            }
        }
    }

    fun destroy() {
        executor.execute {
            model.clearEncryptedData()
        }
        executor.shutdown()
    }
}