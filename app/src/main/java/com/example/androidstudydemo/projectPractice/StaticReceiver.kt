package com.example.androidstudydemo.projectPractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.dmoral.toasty.Toasty

class StaticReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action:String? =intent?.action
        when(action){
            "静态广播" -> {
                // 处理无序广播
                Toasty.success(context!!,"接收到静态广播数据: ${intent.getStringExtra("message")}",Toasty.LENGTH_SHORT).show()
            }

        }
    }
}