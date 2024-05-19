package com.creative.androidfundamentalsbydantech.fragment.mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleMvvmBinding

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVVMFragment : Fragment() {
    companion object {
        fun newInstance(): SimpleMVVMFragment {
            return SimpleMVVMFragment().apply {
                arguments = Bundle()
            }
        }
    }

    private val onClickHandler: OnClickHandler by lazy {
        object : OnClickHandler {
            override fun onSubmitEncryptClick(v: View) {
                val inputText = binding?.inputTextField?.text.toString()
                viewModel.encryptData(inputText)
            }

            override fun onClearClick(v: View) {
                viewModel.clearData()
            }
        }
    }

    private var binding: FragmentSimpleMvvmBinding? = null

    private val viewModel: SimpleMVVMViewModel by lazy {
        ViewModelProvider(this, viewModelFactory { SimpleMVVMViewModel(SimpleMVVMModel()) })[SimpleMVVMViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSimpleMvvmBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserveLiveData()
    }

    private fun setupView() {
        binding?.apply {
            onClickHandler = this@SimpleMVVMFragment.onClickHandler
            inputTextField.doAfterTextChanged {
                viewModel.updateInput(it.toString())
            }
        }
    }
    private fun setupObserveLiveData() {
        Log.d("SimpleMVVMFragment", "setupObserveLiveData ${viewModel.hashCode()}")
        viewModel.apply {
            listEncryptedLiveData.observe(viewLifecycleOwner) {
                binding?.outputText = it
            }
            showMessageLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            inputTextLiveData.observe(viewLifecycleOwner) {
                if (binding?.inputTextField?.text.toString() == it) {
                    return@observe
                }
                binding?.inputTextField?.setText(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    interface OnClickHandler {
        fun onSubmitEncryptClick(v: View)
        fun onClearClick(v: View)
    }
}