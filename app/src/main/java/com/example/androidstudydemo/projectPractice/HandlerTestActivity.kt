package com.example.androidstudydemo.projectPractice

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
import com.example.androidstudydemo.databinding.ActivityHandlerTestBinding
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class HandlerTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHandlerTestBinding

    // 绑定主线程 Looper，负责接收消息并更新界面。
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            when (message.what) {
                0 -> {
                    binding.tvHandler.text = "收到立即消息"
                    Toasty.success(
                        this@HandlerTestActivity,
                        "Handler 已处理消息 0"
                    ).show()
                }

                1 -> {
                    binding.tvHandler.text = "收到延迟消息"
                    Toasty.success(
                        this@HandlerTestActivity,
                        "Handler 已处理消息 1"
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 加载 activity_handler_test.xml。
        binding = ActivityHandlerTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // 由 XML 中 android:onClick="sendMessage" 自动调用。
    fun sendMessage(view: View) {
        thread {
            // 模拟子线程耗时任务。
            Thread.sleep(3000)

            // 立即发送消息 0。
            handler.sendEmptyMessage(0)

            // 5 秒后发送消息 1。
            handler.sendEmptyMessageDelayed(1, 5000)
        }
    }

    override fun onDestroy() {
        // 页面销毁时移除还未执行的消息。
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
