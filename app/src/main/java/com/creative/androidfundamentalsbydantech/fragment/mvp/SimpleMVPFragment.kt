package com.creative.androidfundamentalsbydantech.fragment.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleMvpBinding
import org.json.JSONObject

/**
 * Created by dan on 19/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleMVPFragment : Fragment(), SimpleContract.View {
    companion object {
        fun newInstance(): SimpleMVPFragment {
            return SimpleMVPFragment().apply {
                arguments = Bundle()
            }
        }
    }

    private var binding: FragmentSimpleMvpBinding? = null

    private val presenter: SimpleContract.Presenter by lazy {
        SimpleMVPPresenter(this, SimpleMVPModel())
    }

    private val onClickHandler: OnClickHandler by lazy {
        object : OnClickHandler {
            override fun onSubmitEncryptClick(v: View) {
                val inputText = binding?.inputTextField?.text.toString()
                presenter.encryptData(inputText)
            }

            override fun onClearClick(v: View) {
                presenter.clearData()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSimpleMvpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun setupView() {
       binding?.apply {
            onClickHandler = this@SimpleMVPFragment.onClickHandler
       }
    }

    override fun updateEncryptedText(encryptedText: String) {
        binding?.apply {
            outputText = encryptedText
            executePendingBindings()
        }
    }

    override fun clearInputText() {
        binding?.inputTextField?.setText("")
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("snapshot", presenter.getSnapshotData())
        outState.putString("input", binding?.inputTextField?.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val restoredInput = it.getString("input")
            binding?.inputTextField?.setText(restoredInput)
            binding?.inputTextField?.setSelection(restoredInput?.length ?: 0)
            presenter.restoreSnapshotData(it.getString("snapshot") ?: JSONObject().toString())
        }
    }

    interface OnClickHandler {
        fun onSubmitEncryptClick(v: View)
        fun onClearClick(v: View)
    }
}