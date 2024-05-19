package com.creative.androidfundamentalsbydantech.fragment.mvp

import androidx.fragment.app.Fragment

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPFragment : Fragment(), SimpleContract.View {
    companion object {
        fun newInstance(): SimpleMVPFragment {
            return SimpleMVPFragment().apply {
                arguments = android.os.Bundle()
            }
        }
    }

    override fun updateEncryptedText(text: String) {
        TODO("Not yet implemented")
    }

    override fun clearInputText() {
        TODO("Not yet implemented")
    }
}