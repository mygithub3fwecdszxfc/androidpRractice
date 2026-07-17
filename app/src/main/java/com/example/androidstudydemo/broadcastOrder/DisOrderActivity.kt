package com.example.androidstudydemo.broadcastOrder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityDisOrderBinding
import es.dmoral.toasty.Toasty

class DisOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDisOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.sendButton.setOnClickListener {
            // 创建广播Intent，参数为广播Action（唯一标识，接收方靠Action匹配）
            val intent = Intent("有序广播")
            // putExtra：携带数据，跨页面传递信息
            intent.putExtra("name", "昭爷以雷霆击碎黑暗！！")
            // 发送标准广播
            sendOrderedBroadcast(intent, null)
            Toasty.success(this, "广播已发送", Toast.LENGTH_SHORT).show()

        }
        binding.backButton.setOnClickListener {
            finish()
        }

    }
}