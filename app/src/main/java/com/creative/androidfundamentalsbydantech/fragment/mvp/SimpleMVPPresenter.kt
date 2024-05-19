package com.creative.androidfundamentalsbydantech.fragment.mvp

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPPresenter(
    private val view: SimpleContract.View,
    private val model: SimpleMVPModel
) : SimpleContract.Presenter {
    override fun encryptData(data: String) {
        TODO("Not yet implemented")
    }

    override fun clearData() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }
}