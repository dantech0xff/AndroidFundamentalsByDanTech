package com.creative.androidfundamentalsbydantech.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.fragment.SimpleFragment

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // why have to check this?? Will answer in the course
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SimpleFragment.newInstance("First Fragment", "Hello From FragmentActivity"))
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentActivity", "onDestroy ${this@FragmentActivity.hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentActivity", "onPause ${this@FragmentActivity.hashCode()}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("FragmentActivity", "onRestart ${this@FragmentActivity.hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentActivity", "onStart ${this@FragmentActivity.hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentActivity", "onResume ${this@FragmentActivity.hashCode()}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("FragmentActivity", "onConfigurationChanged ${this@FragmentActivity.hashCode()}")
    }
}