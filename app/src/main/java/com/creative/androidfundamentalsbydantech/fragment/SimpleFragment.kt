package com.creative.androidfundamentalsbydantech.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleBinding

/**
 * Created by dan on 18/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleFragment : Fragment() {

    private var binding: FragmentSimpleBinding? = null

    companion object {
        fun newInstance(name: String, message: String): SimpleFragment {
            return SimpleFragment().apply {
                arguments = Bundle().apply {
                    putString("message", message)
                    putString("name", name)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("SimpleFragment", "onAttach")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SimpleFragment", "onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("SimpleFragment", "onCreateView")
        binding = FragmentSimpleBinding.inflate(inflater, container, false)
        return binding?.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fragmentName?.text = "${arguments?.getString("name")}\n${arguments?.getString("message")}"
        binding?.buttonNextFragment?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    newInstance("Next Fragment ${System.currentTimeMillis()}", "Hello From Next Fragment"),
                    "${this::class.java}-${this.hashCode()}"
                )
                .addToBackStack("${this.hashCode()}")
                .commit()
        }
        binding?.buttonPreviousFragment?.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                Toast.makeText(requireContext(), "BackStack Remaining ${parentFragmentManager.backStackEntryCount}", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "No BackStack Remaining", Toast.LENGTH_SHORT).show()
            }
        }
        Log.d("SimpleFragment", "onViewCreated")
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("SimpleFragment", "onViewStateRestored")
    }
    override fun onStart() {
        super.onStart()
        Log.d("SimpleFragment", "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d("SimpleFragment", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d("SimpleFragment", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d("SimpleFragment", "onStop")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("SimpleFragment", "onSaveInstanceState")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SimpleFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SimpleFragment", "onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.d("SimpleFragment", "onDetach")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("SimpleFragment", "onConfigurationChanged")
    }
}