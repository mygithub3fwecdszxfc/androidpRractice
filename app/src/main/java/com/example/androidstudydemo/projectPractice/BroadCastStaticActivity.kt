package com.example.androidstudydemo.projectPractice


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadCastStaticBinding

    class BroadCastStaticActivity : AppCompatActivity() {
        private lateinit var binding: ActivityBroadCastStaticBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityBroadCastStaticBinding.inflate(layoutInflater)
            setContentView(binding.root)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            binding.btnSendMessage.setOnClickListener {
                val intent = Intent(this, StaticReceiver::class.java)
                intent.action = "静态广播"
                intent.putExtra("message", "Hello Static Broadcast")
                sendBroadcast(intent)
            }
            binding.jump.setOnClickListener {
                val intent = Intent(this, BroadCastOrderActivity::class.java)
                startActivity(intent)
            }
        }
    }

