package com.example.androidstudydemo.broadcastOrder

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.broadcastDemo.MyReceiver
import com.example.androidstudydemo.databinding.ActivityBroadOrderBinding

class BroadOrderActivity : AppCompatActivity() {
    private val messageReceiver = BroadcastOrder()
    private val messageReceiver1 = BroadcastOrder1()
    private val messageReceiver2 = BroadcastOrder2()
    private lateinit var binding: ActivityBroadOrderBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        registerReceivers()
        binding.btnJump.setOnClickListener {
            startActivity(Intent(this, DisOrderActivity::class.java))
        }
    }

//    @SuppressLint("UnspecifiedRegisterReceiverFlag")
//    private fun registerReceivers() {
//        // ========== 第一个接收器：监听无序广播 ==========
//        val filter0 = IntentFilter("无序广播")
//        registerSingleReceiver(messageReceiver, filter0)
//
//        // ========== 第二个接收器：监听有序广播 ==========
//        val filter1 = IntentFilter("有序广播")
//        registerSingleReceiver(messageReceiver1, filter1)
//
//        // ========== 第三个接收器：同时监听两种广播 ==========
//        val filter2 = IntentFilter()
//        filter2.addAction("无序广播")
//        filter2.addAction("有序广播")
//        registerSingleReceiver(messageReceiver2, filter2)
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerReceivers() {

        val filter1 = IntentFilter("无序广播")
        val filter2 = IntentFilter("无序广播")
        val filter3 = IntentFilter("无序广播")
        filter1.addAction("有序广播")
        filter2.addAction("有序广播")
        filter3.addAction("有序广播")
    filter1.priority = 10
        filter2.priority = 50
        filter3.priority = 100
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            registerReceiver(
                messageReceiver,
                filter1,
                RECEIVER_EXPORTED
            )
            registerReceiver(
                messageReceiver1,
                filter2,
                RECEIVER_EXPORTED
            )
            registerReceiver(
                messageReceiver2,
                filter3,
                RECEIVER_EXPORTED
            )

        } else {

            registerReceiver(
                messageReceiver,
                filter1,
                RECEIVER_EXPORTED
            )
            registerReceiver(
                messageReceiver1,
                filter2,
                RECEIVER_EXPORTED
            )
            registerReceiver(
                messageReceiver2,
                filter3,
                RECEIVER_EXPORTED
            )
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(messageReceiver)
        unregisterReceiver(messageReceiver1)
        unregisterReceiver(messageReceiver2)
    }
}