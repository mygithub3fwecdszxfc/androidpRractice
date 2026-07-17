package com.example.androidstudydemo.simplehandler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidstudydemo.databinding.ActivitySimpleHandlerBinding

class SimpleHandlerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleHandlerBinding

    private var workerLooper: SimpleLooper? = null
    private var workerHandler: SimpleHandler? = null
    private var workerThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySimpleHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMessageThread()

        binding.btnSendMessage.setOnClickListener {
            workerHandler?.sendMessage(
                SimpleMessage(
                    what = 1,
                    obj = "这是一条立即执行的消息"
                )
            )
        }

        binding.btnSendDelayedMessage.setOnClickListener {
            workerHandler?.sendMessageDelayed(
                message = SimpleMessage(
                    what = 2,
                    obj = "这是一条延迟3秒执行的消息"
                ),
                delayMillis = 3000L
            )
        }

        binding.btnRemoveMessage.setOnClickListener {
            workerHandler?.removeMessages(2)
        }
    }

    /**
     * 创建消息循环线程
     */
    private fun startMessageThread() {
        workerThread = Thread {
            // 1. 为当前子线程创建Looper和消息队列
            SimpleLooper.prepare()

            workerLooper = SimpleLooper.myLooper()

            // 2. 创建Handler
            workerHandler = object : SimpleHandler() {

                override fun handleMessage(message: SimpleMessage) {
                    val result = when (message.what) {
                        1 -> "收到普通消息：${message.obj}"
                        2 -> "收到延迟消息：${message.obj}"
                        else -> "收到未知消息：${message.what}"
                    }

                    Log.d("SimpleHandler", result)

                    // 当前处于子线程，需要切换到主线程更新界面
                    runOnUiThread {
                        binding.tvMessage.text = result
                    }
                }
            }

            // 3. 开始循环获取消息
            SimpleLooper.loop()

            Log.d("SimpleHandler", "消息循环已退出")
        }.apply {
            name = "SimpleHandlerThread"
            start()
        }
    }

    override fun onDestroy() {
        // 退出消息循环，避免线程泄漏
        workerLooper?.quit()
        workerLooper = null
        workerHandler = null
        workerThread = null

        super.onDestroy()
    }
}