package com.example.androidstudydemo.broadcastDemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadSimpleBinding

class BroadSimpleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadSimpleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.sendButton.setOnClickListener {
            val intent = Intent(this, MyReceiver::class.java)
            intent.action = "传递广播数据"
            intent.putExtra("name", "张三")
            sendBroadcast(intent)
        }
    }
}