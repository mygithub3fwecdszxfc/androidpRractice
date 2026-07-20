package com.example.androidstudydemo.projectPractice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class MyService: Service() {
    private var count = 0
    private var isRunning = false
    private var handler= Handler(Looper.getMainLooper())
    private val runnable=object: Runnable{
        override fun run() {
            count++
            Log.d("MyService", "服务正在运行,当前count值为: $count")
            handler.postDelayed(this,1000)
        }
    }

    /**
     * 创建一个Binder对象，返回给客户端，客户端通过这个Binder对象调用服务端的方法
     */
    private val binder=MyBinder()
    inner class MyBinder: Binder(){
        val service: MyService=this@MyService
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyService", "onBind:服务被绑定 ")
        startCount()
        return binder
    }

    override fun onCreate() {
        Log.d("MyService", "onCreate:服务被创建了 ")
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand:服务被启动了 ")
        startCount()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startCount() {
        if(isRunning)return
        isRunning=true
        handler.post(runnable)
    }
    private fun stopCount() {
        isRunning=false
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        Log.d("MyService", "onDestroy:服务被销毁了 ")
        stopCount()
        super.onDestroy()
    }
    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("MyService", "onUnbind:服务被解绑 ")
        return super.onUnbind(intent)
    }
     fun getCount():Int=count

}