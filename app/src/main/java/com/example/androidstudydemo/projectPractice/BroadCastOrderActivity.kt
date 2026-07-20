package com.example.androidstudydemo.projectPractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadCastOrderBinding
import es.dmoral.toasty.Toasty


class BroadCastOrderActivity : AppCompatActivity() {
    private val receiver1 = object : BroadcastReceiver() {
        //有序广播有优先级链，高优先级的 Receiver 可以修改数据再传给下一个 Receiver。所以数据不在 Intent 里，而是在广播结果中
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "有序广播" -> {
                    Toasty.success(this@BroadCastOrderActivity, "【优先级100】接收到有序广播，数据为:${intent.getStringExtra("name")}", Toast.LENGTH_LONG).show()
                    setResultData("张志鹏修改")
                }
            }
        }
    }
    private val receiver2 = object : BroadcastReceiver() {
        //有序广播有优先级链，高优先级的 Receiver 可以修改数据再传给下一个 Receiver。所以数据不在 Intent 里，而是在广播结果中
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {

                "有序广播" -> {
                    Toasty.success(this@BroadCastOrderActivity, "【优先级50】接收到有序广播,数据为:${getResultData()}", Toast.LENGTH_LONG).show()
                    // 创建Bundle，存放多条数据
                    val bundle = Bundle()
                    bundle.putString("name", "张三")
                    bundle.putInt("age", 22)
                    bundle.putString("sex", "男")
                    setResultExtras(bundle)
                }
            }
        }
    }
    private val receiver3 = object : BroadcastReceiver() {
        //有序广播有优先级链，高优先级的 Receiver 可以修改数据再传给下一个 Receiver。所以数据不在 Intent 里，而是在广播结果中
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {

                "有序广播" -> {
                    val bundle = getResultExtras(true)
                   //读取
                    val name = bundle?.getString("name")
                    val age = bundle?.getInt("age")
                    val sex = bundle?.getString("sex")
                    Toasty.success(this@BroadCastOrderActivity, "【优先级10】接收到有序广播,数据为:$name,$age,$sex", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private lateinit var binding: ActivityBroadCastOrderBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadCastOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        registerReceiver()

        binding.sendMessage.setOnClickListener {
            val intent = Intent("有序广播")
            intent.putExtra("name", "张志鹏")
            sendOrderedBroadcast(intent,null)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerReceiver() {
        val filter1 = IntentFilter().apply {
            addAction("有序广播")
            priority = 100
        }
        val filter2 = IntentFilter().apply {
            addAction("有序广播")
            priority = 50
        }
        val filter3 = IntentFilter().apply {
            addAction("有序广播")
            priority = 10
        }
        registerReceiver(receiver1, filter1, RECEIVER_EXPORTED)
        registerReceiver(receiver2, filter2,RECEIVER_EXPORTED)
        registerReceiver(receiver3, filter3,RECEIVER_EXPORTED)


    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver1)
        unregisterReceiver(receiver2)
        unregisterReceiver(receiver3)
    }
}