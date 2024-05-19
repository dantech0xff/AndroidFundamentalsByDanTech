package com.creative.androidfundamentalsbydantech.fragment.mvp

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

interface SimpleContract {
    interface View {
        fun updateEncryptedText(text: String)
        fun clearInputText()
    }

    interface Presenter {
        fun encryptData(data: String)
        fun clearData()
        fun destroy()
    }
}