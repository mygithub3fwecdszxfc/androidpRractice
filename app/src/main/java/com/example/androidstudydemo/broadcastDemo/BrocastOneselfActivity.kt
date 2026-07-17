package com.example.androidstudydemo.broadcastDemo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBrocastOneselfBinding
import es.dmoral.toasty.Toasty

class BrocastOneselfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBrocastOneselfBinding
    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
            val action: String? = intent?.action
            if(action == "雷神开播了，真的假的!"){
                binding.tvContent.text = "雷神开播了，真的假的!"
                Toasty.success(this@BrocastOneselfActivity, "雷神开播了，真的假的!", Toast.LENGTH_SHORT, true).show()
            }
            else if(action == "阿雷，你牛大了!"){
                binding.tvContent.text = "阿雷，你牛大了!"
                Toasty.success(this@BrocastOneselfActivity, "阿雷，你牛大了!", Toast.LENGTH_SHORT, true).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBrocastOneselfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val filter = IntentFilter("雷神开播了，真的假的!")
        registerReceivers()
        binding.btnSend.setOnClickListener {
            // 创建广播Intent，参数为广播Action（唯一标识，接收方靠Action匹配）
            val intent = Intent("雷神开播了，真的假的!")
            // putExtra：携带数据，页面传递信息
            intent.putExtra("name", "zhangsan")
            // 发送标准广播
            sendBroadcast(intent)
        }
        binding.jump.setOnClickListener {
            startActivity(Intent(this, BrocastOneself1Activity::class.java))

        }
    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerReceivers() {
        val filter = IntentFilter("雷神开播了，真的假的!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android13及以上 使用5参数重载，传入flag
            registerReceiver(messageReceiver, filter, RECEIVER_EXPORTED,)
            Toasty.success(this, "注册本页面广播接收器成功自己的自己的", Toasty.LENGTH_SHORT).show()
        }

        val filter1 = IntentFilter("阿雷，你牛大了!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android13及以上 使用5参数重载，传入flag
            registerReceiver(messageReceiver, filter1, RECEIVER_EXPORTED,)
            Toasty.success(this, "注册页面间广播接收器成功别人的别人的", Toasty.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(messageReceiver)
    }
}