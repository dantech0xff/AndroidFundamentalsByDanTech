package com.creative.androidfundamentalsbydantech.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var viewBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerClickEvent()

        viewBinding?.textView?.text = "MainActivity Task Id: ${this.taskId}\nInstance Id: ${this.hashCode()}"
    }

    private fun registerClickEvent() {
        viewBinding?.apply {
            startStandardActivityButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, StandardActivity::class.java))
            }
            startSingleTopActivityButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SingleTopActivity::class.java))
            }
            startSingleTaskActivityButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SingleTaskActivity::class.java))
            }
            startSingleInstanceActivityButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SingleInstanceActivity::class.java))
            }
        }
    }
}