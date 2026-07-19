package com.example.androidstudydemo.broadcastOrder

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadLocalBinding
import es.dmoral.toasty.Toasty

/**
 * 【本地广播 LocalBroadcast 专属页面】
 * 本地广播特点：
 * 1. 仅当前APP内部通信，外部APP拦截不到、发不了
 * 2. 不需要适配Android13多参数注册
 * 3. 无序、无优先级、不可截断、不能setResultData
 * 4. 安全性远高于系统全局广播
 */
class BroadLocalActivity : AppCompatActivity() {

    // 初始化本地广播接收器（修复你语法错误：var改为val，去掉括号写法）
    private val localReceiver = BroadcastOrder()

    // 视图绑定
    private lateinit var binding: ActivityBroadLocalBinding

    // 本地广播管理器核心对象
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 沉浸式状态栏适配
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化本地广播管理器
        localBroadcastManager = LocalBroadcastManager.getInstance(this)

        // 注册本地广播
        registerLocalReceiver()
        binding.btnLocal.setOnClickListener {
            val intent = Intent("无序广播")
            intent.putExtra("nickname", "无序广播巴啦啦小魔仙全身变")
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    /**
     * 本地广播注册方法
     * 和系统广播不同：无需版本判断、无需flag、写法极简
     */
    private fun registerLocalReceiver() {
        val filter = IntentFilter()
        filter.addAction("无序广播")
        filter.addAction("有序广播")
        //添加一些数据

        // 本地广播专属注册方法
        localBroadcastManager.registerReceiver(localReceiver, filter)
        Toasty.success(this, "本地广播注册成功", Toasty.LENGTH_SHORT).show()
    }

    /**
     * 本地广播必须手动解绑，防止内存泄漏
     */
    override fun onDestroy() {
        super.onDestroy()
        // 专属解绑方法
        localBroadcastManager.unregisterReceiver(localReceiver)
    }
}