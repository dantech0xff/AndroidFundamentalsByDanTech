package com.creative.androidfundamentalsbydantech.activity

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.creative.androidfundamentalsbydantech.R
import com.creative.androidfundamentalsbydantech.databinding.ActivityMainBinding
import com.creative.androidfundamentalsbydantech.receiver.SimpleReceiver
import com.creative.androidfundamentalsbydantech.service.BackgroundService
import com.creative.androidfundamentalsbydantech.service.ForegroundService

class MainActivity : AppCompatActivity() {
    private val uiHandler: Handler = Handler(Looper.getMainLooper())
    private val backgroundHandlerThread = HandlerThread("Background-HandlerThread").apply {
        start()
    }
    private val backgroundHandler: Handler by lazy { Handler(backgroundHandlerThread.looper) }

    private var viewBinding: ActivityMainBinding? = null

    private var bgServiceId: Int = 0
    private var fgServiceId: Int = 1

    private val simpleReceiver = SimpleReceiver()

    private val requestSmsPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val smsList = fetchSmsHistory(contentResolver)
            Log.d("MainActivity", "SMS List: $smsList")
            Toast.makeText(this, "Fetched SMS List Size: ${smsList.size}", Toast.LENGTH_LONG).show()
        } else {
            Log.d("MainActivity", "SMS Permission denied")
        }
    }

    private fun runUITask() {
        uiHandler.post {
            // Run UI Task
            Log.d("MainActivity", "Run UI Task ${Thread.currentThread().name}")
            Toast.makeText(this@MainActivity, "Run UI Task ${Thread.currentThread().name}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun runBackgroundTask() {
        backgroundHandler.post {
            // Run Background Task
            Log.d("MainActivity", "Run Background Task ${Thread.currentThread().name}")
            Toast.makeText(this@MainActivity, "Run Background Task ${Thread.currentThread().name}", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
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

        // Register for Receiver, put it in onCreate of Activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(simpleReceiver, IntentFilter().apply {
                addAction(SimpleReceiver.SIMPLE_ACTION)
            }, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(simpleReceiver, IntentFilter().apply {
                addAction(SimpleReceiver.SIMPLE_ACTION)
            })
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(simpleReceiver)
        backgroundHandlerThread.quitSafely()
    }

    private fun startBackgroundService(id: Int) {
        val intent = Intent(this, BackgroundService::class.java).putExtra("dataField", "data_$id")
        startService(intent)
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

            startBackgroundServiceButton.setOnClickListener {
                startBackgroundService(bgServiceId++)
            }
            stopBackgroundServiceButton.setOnClickListener {
                stopService(Intent(this@MainActivity, BackgroundService::class.java))
            }

            startForeGroundServiceButton.setOnClickListener {
                ++fgServiceId
                val intent = Intent(this@MainActivity, ForegroundService::class.java)
                    .putExtra("dataField", "data_$fgServiceId")
                    .putExtra("id", fgServiceId)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
            }
            stopForegroundServiceButton.setOnClickListener {
                stopService(Intent(this@MainActivity, ForegroundService::class.java))
            }
            broadcastSimpleReceiverButton.setOnClickListener {
                sendBroadcast(Intent(SimpleReceiver.SIMPLE_ACTION))
            }
            fetchSmsHistoryButton.setOnClickListener {
                requestSmsPermission.launch(android.Manifest.permission.READ_SMS)
            }
            launchFragmentButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, FragmentActivity::class.java))
            }
            startUiTaskButton.setOnClickListener {
                runUITask()
            }
            startBackgroundTaskButton.setOnClickListener {
                runBackgroundTask()
            }
        }
    }

    private fun fetchSmsHistory(contentResolver: ContentResolver): List<String> {
        val smsList = mutableListOf<String>()
        val cursor: Cursor? = contentResolver.query(
            // URI trỏ đến bảng SMS
            Telephony.Sms.CONTENT_URI,
            // projection: các column cần lấy ra
            null,
            // selection: Điều kiện WHERE
            null,
            // selectionArgs: Tham số điều kện Where
            null,
            // sortOrder: Sắp xếp thứ tự
            Telephony.Sms.DATE + " DESC"
        )

        cursor?.use {
            val bodyIndex = it.getColumnIndex(Telephony.Sms.BODY)
            val addressIndex = it.getColumnIndex(Telephony.Sms.ADDRESS)
            val dateIndex = it.getColumnIndex(Telephony.Sms.DATE)

            while (it.moveToNext()) {
                val smsBody = it.getString(bodyIndex)
                val smsAddress = it.getString(addressIndex)
                val smsDate = it.getLong(dateIndex)
                smsList.add("SMS from $smsAddress at $smsDate: $smsBody")
            }
        }

        return smsList
    }
}