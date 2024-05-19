package com.creative.androidfundamentalsbydantech.fragment.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class SimpleMVVMViewModel(private val model: SimpleMVVMModel) : ViewModel() {

    private val _listEncryptedLiveData = MutableLiveData<String>()
    val listEncryptedLiveData: LiveData<String> = _listEncryptedLiveData

    private val _showMessageLiveData = MutableLiveData<String>()
    val showMessageLiveData: LiveData<String> = _showMessageLiveData

    private val _inputTextLiveData = MutableLiveData<String>()
    val inputTextLiveData: LiveData<String> = _inputTextLiveData

    private val executor by lazy {
        ThreadPoolExecutor(
            1, 1,
            3, TimeUnit.SECONDS, LinkedBlockingDeque()
        )
    }

    fun updateInput(input: String) {
        _inputTextLiveData.postValue(input)
    }

    private fun internalEncryptData(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec(model.getSecretKey().toByteArray(), "AES")
        )
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return String(encryptedBytes)
    }

    fun encryptData(data: String) {
        if (data.isNotEmpty()) {
            executor.execute {
                val encrypted = internalEncryptData(data)
                model.addEncryptedData(data, encrypted)
                _listEncryptedLiveData.postValue(model.getListEncryptedData())
                _inputTextLiveData.postValue("")
            }
        } else {
            _showMessageLiveData.postValue("Please input data")
        }
    }

    fun clearData() {
        executor.execute {
            model.clearEncryptedData()
            _listEncryptedLiveData.postValue(model.getListEncryptedData())
            _inputTextLiveData.postValue("")
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.execute {
            model.clearEncryptedData()
        }
        executor.shutdown()

        Log.d("SimpleMVVMViewModel", "onCleared")
    }
}

inline fun <VM : ViewModel> viewModelFactory(crossinline function: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = function() as T
    }