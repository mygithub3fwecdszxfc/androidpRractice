package com.example.androidstudydemo.broadcastDemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBrocastOneself1Binding

class BrocastOneself1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBrocastOneself1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBrocastOneself1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.sendButton.setOnClickListener {
            // 创建广播Intent，参数为广播Action（唯一标识，接收方靠Action匹配）
            val intent = Intent("阿雷，你牛大了!")
            // putExtra：携带数据，页面传递信息
            intent.putExtra("name", "张三数据")
            // 发送标准广播
            sendBroadcast(intent)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }


}