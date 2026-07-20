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

// 模拟要传递的自定义数据实体
data class TaskData(val taskName: String, val progress: Int)

class HandlerTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHandlerTestBinding

    // 绑定主线程 Looper，负责接收消息并更新界面。
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            when (message.what) {
                0 -> {
                    binding.tvHandler.text = "消息0"
                    Toasty.success(
                        this@HandlerTestActivity,
                        "Handler 已处理消息 0"
                    ).show()
                }
                1 -> {
                    binding.tvHandler.text = "消息1"
                    Toasty.success(
                        this@HandlerTestActivity,
                        "Handler 已处理消息 1"
                    ).show()
                }
                // 新增：携带obj、arg1、arg2的自定义消息
                2 -> {
                    // 取出int参数
                    val num1 = message.arg1
                    val num2 = message.arg2
                    // 取出obj实体数据
                    val data = message.obj as String
                    binding.tvHandler.text = data
                    Toasty.success(this@HandlerTestActivity, "Handler 已处理消息2").show()
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

            // 方式1：空消息，仅带what
            handler.sendEmptyMessage(0)

            // 5 秒后发送消息 1。
            handler.sendEmptyMessageDelayed(1, 2000)

//            Message.obtain(handler)：从系统全局缓存池取出复用的 Message 对象，不用 new，减少内存抖动；
//            what：int 类型，区分不同消息；
//            arg1、arg2：轻量 int 参数，传简单数字；
//            obj：Any 类型，可以传递字符串、自定义实体类、列表等任意数据；
//            sendMessage(msg) / sendMessageDelayed(msg, 延迟毫秒) 发送完整带数据消息
            // ====================== 新增：使用Message消息池，携带 obj / what / arg1 / arg2 ======================
            // 1. 从消息池获取复用的Message对象,绑定对应的handler
            val msg = Message.obtain(handler)
            // 2. 设置消息标识what
            msg.what = 2
            // 3. 传递两个轻量int参数
            msg.arg1 = 60
            msg.arg2 = 100
            // 4. obj传递自定义对象
            msg.obj = "消息2"
            // 发送这条完整消息
            handler.sendMessageDelayed(msg,5000)
        }
    }

    override fun onDestroy() {
        // 页面销毁时移除还未执行的消息。
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}