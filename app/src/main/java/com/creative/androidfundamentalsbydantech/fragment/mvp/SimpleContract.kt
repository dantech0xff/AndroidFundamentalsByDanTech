package com.creative.androidfundamentalsbydantech.fragment.mvp

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

interface SimpleContract {
    interface View {
        fun setupView()
        fun updateEncryptedText(text: String)
        fun clearInputText()
        fun showMessage(message: String)
    }

    interface Presenter {
        fun encryptData(data: String)
        fun clearData()
        fun getSnapshotData(): String
        fun restoreSnapshotData(snapshot: String)
        fun destroy()
    }
}