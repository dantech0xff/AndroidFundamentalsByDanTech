package com.creative.androidfundamentalsbydantech.fragment.mvp

import android.os.Handler
import android.os.Looper
import android.util.Base64
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPPresenter(
    private val view: SimpleContract.View,
    private val model: SimpleMVPModel
) : SimpleContract.Presenter {

    private val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val executor by lazy {
        ThreadPoolExecutor(
            1, 1,
            3, TimeUnit.SECONDS, LinkedBlockingDeque()
        )
    }

    private fun internalEncryptData(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE,
            SecretKeySpec(model.getSecretKey().toByteArray(), "AES"))
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return String(encryptedBytes)
    }

    override fun encryptData(data: String) {
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

    override fun clearData() {
        executor.execute {
            model.clearEncryptedData()
            mainHandler.post {
                view.updateEncryptedText(model.getListEncryptedData())
                view.clearInputText()
            }
        }
    }

    override fun getSnapshotData(): String {
        val listEncrypted = model.getListEncrypted()
        val jsonObject = JSONObject()
        listEncrypted.forEach {
            jsonObject.put(it.key, it.value)
        }
        return jsonObject.toString()
    }

    override fun restoreSnapshotData(snapshot: String) {
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

    override fun destroy() {
        executor.execute {
            model.clearEncryptedData()
        }
        executor.shutdown()
    }
}