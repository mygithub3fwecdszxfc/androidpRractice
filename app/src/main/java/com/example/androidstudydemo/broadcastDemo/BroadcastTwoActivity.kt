package com.example.androidstudydemo.broadcastDemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadcastTwoBinding
import es.dmoral.toasty.Toasty

/**
 * 广播发送页面
 * 发送自定义全局广播，同一个App内注册了对应Action的接收器可以接收消息
 */
class BroadcastTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadcastTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 点击按钮发送自定义广播
        binding.btnSendBroadcast.setOnClickListener {
            // 创建广播Intent，参数为广播Action（唯一标识，接收方靠Action匹配）
            val intent = Intent("com.example.androidstudydemo.MY_BROADCAST")
            // putExtra：携带数据，跨页面传递信息
            intent.putExtra("name", "zhangsan")
            // 发送标准广播
            sendBroadcast(intent)
//            Toasty.success(this, "广播已成功发送", Toasty.LENGTH_SHORT).show()
//            finish()
        }
    }
}