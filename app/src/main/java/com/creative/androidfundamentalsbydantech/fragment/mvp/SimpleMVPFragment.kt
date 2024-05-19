package com.creative.androidfundamentalsbydantech.fragment.mvp

import androidx.fragment.app.Fragment

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPFragment : Fragment() {
    companion object {
        fun newInstance(): SimpleMVPFragment {
            return SimpleMVPFragment().apply {
                arguments = android.os.Bundle()
            }
        }
    }
}