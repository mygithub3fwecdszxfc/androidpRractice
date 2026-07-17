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
import com.example.androidstudydemo.databinding.ActivitySendMessageBinding
import com.example.androidstudydemo.ui.activity.Person
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class SendMessageActivity : AppCompatActivity() {
    private val handler: Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                0 -> {
                    println("handleMessage 当前线程：" + Thread.currentThread().name)
                    //处理消息
                    Toasty.success(this@SendMessageActivity, "收到0消息").show()
                }

                1 -> {
                    val person = msg.obj as Person
                    binding.tvHandler.text = "收到1消息:${person.name}"
                    Toasty.success(this@SendMessageActivity, "收到1消息:${person.name}").show()
                }
            }
        }
    }
    private lateinit var binding: ActivitySendMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySendMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * 方式1：sendMessage 模式，收发分离，配合 handleMessage
     */
    fun sendMessage(view: View) {
        thread {
            println("thread 当前线程：" + Thread.currentThread().name)
            // 模拟2秒耗时任务（网络请求、文件读取等），当前运行在子线程
            Thread.sleep(2000)

            // 获取复用的Message对象，避免频繁new Message造成内存开销,优先从缓存池拿现成对象，没有空闲对象才new
            val message = Message.obtain()
            message.what = 1                 // 消息标识，用于区分不同消息
            message.obj = Person("张三", 18,"cyn") // 携带需要传递的数据

            // 向主线程Handler发送消息，最终在handleMessage中处理、更新UI
            handler.sendMessage(message)
        }
    }

    /**
     * 方式2：post / postDelayed 模式
     * 发送逻辑 + UI代码写在一起，不需要 handleMessage 接收
     */
    fun postMessage(view: View) {
        // 立即投递任务到主线程执行
        handler.post {
            println("post 当前线程：${Thread.currentThread().name}")
            binding.tvHandler.text = "post 立即执行"
            Toasty.success(this@SendMessageActivity, "post 任务运行").show()
        }

        // 延时2000ms投递任务
        handler.postDelayed({
            println("postDelayed 当前线程：${Thread.currentThread().name}")
            binding.tvHandler.text = "postDelayed 延时2秒执行"
            Toasty.success(this@SendMessageActivity, "postDelayed 任务运行").show()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 页面销毁清空所有消息，防止内存泄漏、页面销毁后弹窗/更新UI崩溃
        handler.removeCallbacksAndMessages(null)
    }
}