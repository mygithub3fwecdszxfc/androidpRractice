package com.example.androidstudydemo.serviceDemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.androidstudydemo.R


/**
 * 普通前台/后台启动服务示例（startService方式启动）
 * 功能：内部开启计时器，每秒count自增并打印日志
 */
class MyServiceDemo1 : Service() {
    // 通知渠道ID
    private val CHANNEL_ID = "demo_service_channel"
    // 前台服务通知唯一ID（不能为0！）
    private val NOTIFY_ID = 1001
    // 计数值
    private var count = 0
    // 标记计时器任务是否正在运行，防止重复多次post任务
    private var isRunning = false
    // 主线程Handler，用于循环执行定时任务
    private val handler = Handler(Looper.getMainLooper())

//    fun getService(): MyServiceDemo1 {
//        return MyServiceDemo1()
//    }
//MyServiceDemo1() 代表新建一个全新的 Service 实例。
//Android 系统里，Service 是由系统统一创建管理，你不能自己手动 new Service！

    // 内部类，继承系统 Binder
    inner class MyBinder : Binder(){
        // 对外提供方法：拿到外部Service实例
        fun getService(): MyServiceDemo1 = this@MyServiceDemo1
    }
    // 创建Binder实例对象
    private val binder = MyBinder()

    /**
     * 定时循环任务：每秒执行一次
     */
    private val runnable = object : Runnable {
        override fun run() {
            count++
            Log.d("MyServiceDemo1", "当前count值为：count=$count")
            // 延迟1秒再次投递自身，实现循环计时
            handler.postDelayed(this, 1000)
        }
    }

    /**
     * 服务第一次创建时执行，只会执行一次
     */
    override fun onCreate() {
        Log.d("服务启动ing", "onCreate:服务启动了! ")
        super.onCreate()
        // 创建通知渠道！！
        createNotificationChannel()
    }


    /**
     * startService 每次调用都会触发onStartCommand
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startCount()
        // 开启前台服务核心代码
        val notification = buildNotification()
        startForeground(NOTIFY_ID, notification)
        return super.onStartCommand(intent, flags, startId)
    }
    /**
     * bindService 绑定服务时触发
     * 返回自定义binder，供客户端拿到服务实例
     */
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    /**
     * 服务销毁，释放资源、停止循环任务
     */
    override fun onDestroy() {
        Log.d("服务销毁ing", "onDestroy: 服务销毁了")
        stopCount()
        // 退出前台并移除通知
        stopForeground(Service.STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }
    /**
     * 开启计时器，加判断避免多次启动重复循环
     */
    private fun startCount() {
        if (isRunning) return
        isRunning = true
        handler.post(runnable)
    }

    /**
     * 停止计时任务：移除回调、修改运行标记
     */
    private fun stopCount() {
        handler.removeCallbacks(runnable)
        isRunning = false
    }

    fun getCount(): Int {
        return count
    }

//    override fun onUnbind(intent: Intent?): Boolean {
//        return super.onUnbind(intent)
//    }
//    override fun onRebind(intent: Intent?) {
//        super.onRebind(intent)
//    }
    // ====================== 前台服务新增方法 ======================
    /**
     * 创建通知渠道（Android8.0强制）
     */
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "计时服务通道",
                NotificationManager.IMPORTANCE_HIGH // LOW：不会弹出横幅，仅状态栏小图标
            ).apply {
                description = "前台计时服务通知"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * 构建前台服务通知
     * NotificationCompat.Builder：兼容库构建器，适配高低不同Android系统版本
     */
    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            // 设置通知大标题（通知栏可见）
            .setContentTitle("计时前台服务运行中")
            // 设置通知正文描述
            .setContentText("正在后台持续计数")
            // 设置通知状态栏小图标【必填！缺失直接崩溃】
            .setSmallIcon(R.drawable.sports)
            // setOngoing(true)：设置为常驻通知
            // 用户无法手动上滑清除这条通知，只有服务停止、退出前台才会消失
            .setOngoing(true)
            // build()：组装所有配置，生成最终Notification通知对象
            .build()
    }
}