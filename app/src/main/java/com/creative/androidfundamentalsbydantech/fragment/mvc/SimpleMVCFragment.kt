package com.creative.androidfundamentalsbydantech.fragment.mvc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleMvcBinding
import org.json.JSONObject

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVCFragment : Fragment() {
    companion object {
        fun newInstance(): SimpleMVCFragment {
            return SimpleMVCFragment().apply {
                arguments = Bundle()
            }
        }
    }

    private var binding: FragmentSimpleMvcBinding? = null
    private val controller: SimpleMVCController by lazy {
        SimpleMVCController(this, SimpleMVCModel())
    }

    private val onClickHandler: OnClickHandler by lazy {
        object : OnClickHandler {
            override fun onSubmitEncryptClick(v: View) {
                val inputText = binding?.inputTextField?.text.toString()
                controller.encryptData(inputText)
            }

            override fun onClearClick(v: View) {
                controller.clearData()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSimpleMvcBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    @MainThread
    private fun setupView() {
        binding?.apply {
            onClickHandler = this@SimpleMVCFragment.onClickHandler
        }
    }

    @MainThread
    fun updateEncryptedText(encryptedText: String) {
        binding?.apply {
            outputText = encryptedText
            executePendingBindings()
        }
    }

    @MainThread
    fun clearInputText() {
        binding?.inputTextField?.setText("")
    }

    @MainThread
    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("snapshot", controller.getSnapshotData())
        outState.putString("input", binding?.inputTextField?.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val restoredInput = it.getString("input")
            binding?.inputTextField?.setText(restoredInput)
            binding?.inputTextField?.setSelection(restoredInput?.length ?: 0)
            controller.restoreSnapshotData(it.getString("snapshot") ?: JSONObject().toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.destroy()
    }

    interface OnClickHandler {
        fun onSubmitEncryptClick(v: View)
        fun onClearClick(v: View)
    }
}