package com.creative.androidfundamentalsbydantech.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.databinding.FragmentArchitectureBinding
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleBinding
import com.creative.androidfundamentalsbydantech.fragment.mvc.SimpleMVCFragment

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
                    .add(
                        R.id.fragment_container,
                        SimpleMVCFragment.newInstance(), SimpleMVCFragment::class.java.name
                    )
                    .addToBackStack(SimpleMVCFragment::class.java.name)
                    .commit()
            }

            override fun onMVPClick() {
                Log.d("ArchitectureFragment", "onMVPClick")
                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container,
                        SimpleMVCFragment.newInstance(), SimpleMVCFragment::class.java.name
                    )
                    .addToBackStack(SimpleMVCFragment::class.java.name)
                    .commit()
            }

            override fun onMVVMClick() {
                Log.d("ArchitectureFragment", "onMVVMClick")
                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container,
                        SimpleMVCFragment.newInstance(), SimpleMVCFragment::class.java.name
                    )
                    .addToBackStack(SimpleMVCFragment::class.java.name)
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
        fun onMVPClick()
        fun onMVVMClick()
    }
}