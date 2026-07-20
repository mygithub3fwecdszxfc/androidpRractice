package com.example.androidstudydemo.projectPractice

import android.content.BroadcastReceiver
import android.content.Context
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
import com.example.androidstudydemo.databinding.ActivityBroadcastDynamicBinding
import es.dmoral.toasty.Toasty

class BroadcastDynamicActivity : AppCompatActivity() {

    // 修复匿名对象语法，小驼峰规范命名，全局唯一接收器实例
    private val broadcastDynamicReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action

            when (action) {
                "动态广播1" -> {
                    val name = intent.getStringExtra("name") ?: ""
                    Toasty.success(context!!, "接收到动态广播1", Toasty.LENGTH_SHORT).show()
                    binding.tvText.text = name
                }
                "动态广播2" -> {
                    val name = intent.getStringExtra("name") ?: ""
                    Toasty.success(context!!, "接收到动态广播2", Toasty.LENGTH_SHORT).show()
                    binding.tvText.text = name
                }
            }
        }
    }

    private lateinit var binding: ActivityBroadcastDynamicBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadcastDynamicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.sendMessage1.setOnClickListener {
            val intent = Intent("动态广播1")
            intent.putExtra("name", "张志鹏1")
            sendBroadcast(intent)
        }
        binding.sendMessage2.setOnClickListener {
            val intent = Intent("动态广播2")
            intent.putExtra("name", "张志鹏2")
            sendBroadcast(intent)
        }
        binding.jump.setOnClickListener {
            val intent = Intent(this, BroadCastStaticActivity::class.java)
            startActivity(intent)
        }

        registerBroadcast()
    }

    // 规范方法名，注册唯一接收器实例，不再重复创建
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerBroadcast() {
        val intentFilter = IntentFilter().apply {
            addAction("动态广播1")
            addAction("动态广播2")
        }
        registerReceiver(broadcastDynamicReceiver, intentFilter,RECEIVER_EXPORTED)
    }

    // 必加：页面销毁取消注册，解决内存泄漏
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastDynamicReceiver)
    }
}