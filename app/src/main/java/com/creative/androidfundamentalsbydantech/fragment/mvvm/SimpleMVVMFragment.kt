package com.creative.androidfundamentalsbydantech.fragment.mvvm

import androidx.fragment.app.Fragment

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVVMFragment : Fragment() {
    companion object {
        fun newInstance(): SimpleMVVMFragment {
            return SimpleMVVMFragment().apply {
                arguments = android.os.Bundle()
            }
        }
    }
}