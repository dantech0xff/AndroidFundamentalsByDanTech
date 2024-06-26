package com.creative.androidfundamentalsbydantech.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.databinding.FragmentArchitectureBinding
import com.creative.androidfundamentalsbydantech.fragment.mvc.SimpleMVCFragment
import com.creative.androidfundamentalsbydantech.fragment.mvp.SimpleMVPFragment
import com.creative.androidfundamentalsbydantech.fragment.mvvm.SimpleMVVMFragment

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class ArchitectureFragment : Fragment() {

    private var binding: FragmentArchitectureBinding? = null

    private val onClickHandler by lazy {
        object : OnClickHandler {
            override fun onMVCClick(v: View) {
                Log.d("ArchitectureFragment", "onMVCClick")
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        SimpleMVCFragment.newInstance(),
                        "${SimpleMVCFragment::class.java.name}-${System.currentTimeMillis()}"
                    )
                    .addToBackStack("${SimpleMVCFragment::class.java.name}-${System.currentTimeMillis()}")
                    .commit()
            }

            override fun onMVPClick(v: View) {
                Log.d("ArchitectureFragment", "onMVPClick")
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        SimpleMVPFragment.newInstance(), "${SimpleMVPFragment::class.java.name}-${System.currentTimeMillis()}",
                    )
                    .addToBackStack("${SimpleMVPFragment::class.java.name}-${System.currentTimeMillis()}")
                    .commit()
            }

            override fun onMVVMClick(v: View) {
                Log.d("ArchitectureFragment", "onMVVMClick")
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        SimpleMVVMFragment.newInstance(), "${SimpleMVVMFragment::class.java.name}-${System.currentTimeMillis()}"
                    )
                    .addToBackStack("${SimpleMVVMFragment::class.java.name}-${System.currentTimeMillis()}")
                    .commit()
            }
        }
    }

    companion object {
        fun newInstance(): ArchitectureFragment {
            return ArchitectureFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArchitectureBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            onClickHandler = this@ArchitectureFragment.onClickHandler
        }
    }

    interface OnClickHandler {
        fun onMVCClick(v: View)
        fun onMVPClick(v: View)
        fun onMVVMClick(v: View)
    }
}