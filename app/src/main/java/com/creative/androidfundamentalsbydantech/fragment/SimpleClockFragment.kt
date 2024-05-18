package com.creative.androidfundamentalsbydantech.fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.creative.androidfundamentalsbydantech.databinding.FragmentSimpleClockBinding

/**
 * Created by dan on 18/5/24
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class SimpleClockFragment : Fragment() {
    private var binding: FragmentSimpleClockBinding? = null

    companion object {
        fun newInstance(): SimpleClockFragment {
            return SimpleClockFragment()
        }
    }

    private val clockHandlerThread: HandlerThread = HandlerThread("Clock-Handler-Thread").apply {
        start()
    }
    private val clockHandler: Handler by lazy {
        Handler(clockHandlerThread.looper)
    }
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private val clockRunnable: Runnable = object : Runnable {
        override fun run() {
            Log.d("SimpleClockFragment", "Clock Runnable At Time ${System.currentTimeMillis()} On Thread ${Thread.currentThread().name}")
            mainHandler.post {
                updateTime()
            }
            clockHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSimpleClockBinding.inflate(inflater, container, false)
        binding?.buttonPopBackstack?.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startClock()
    }

    private fun startClock() {
        updateTime()
        clockHandler.postDelayed(clockRunnable, 1000)
    }

    private fun updateTime() {
        binding?.timeNow = Calendar.getInstance().time.toString()
        binding?.executePendingBindings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mainHandler.removeCallbacksAndMessages(null)
        clockHandlerThread.quitSafely()
    }
}