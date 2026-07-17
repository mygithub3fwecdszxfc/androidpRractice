package com.example.androidstudydemo.aidlDemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RemotePersonService : Service() {
    private val TAG = "AIDL服务端日志"

    private val binder = object : IRemotePersonService.Stub() {
        override fun add(a: Int, b: Int): Int {
            Log.d(TAG, "收到客户端加法调用：a=$a  b=$b")
            return a + b
        }

        override fun getPersonInfo(): MyPerson {
            Log.d(TAG, "客户端请求MyPerson实体数据")
            return MyPerson(name = "张三", age = 20)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "客户端绑定远程独立进程服务成功")
        return binder
    }
}