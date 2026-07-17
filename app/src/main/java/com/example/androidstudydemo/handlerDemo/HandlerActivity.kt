package com.example.androidstudydemo.handlerDemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityHandlerBinding
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class HandlerActivity : AppCompatActivity() {
    private val handler: Handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                0 -> {
                    binding.tvHandler.text = "Handler接受数据巴拉巴拉"
                    Toasty.success(this@HandlerActivity, "Handler成功处理数据").show()
                }
                1 -> {
                    binding.tvHandler.text = "Handler拉卜卜步步"
                    Toasty.success(this@HandlerActivity, "Handler成功处理数据").show()
                }
            }
        }
    }
    private lateinit var binding: ActivityHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun sendMessage(view: View) {
        thread {
            println("thread当前线程：${Thread.currentThread().name}")
            Thread.sleep(3000)
            handler.sendEmptyMessage(0)
            handler.sendEmptyMessageDelayed(1, 5000)
        }
    }
}