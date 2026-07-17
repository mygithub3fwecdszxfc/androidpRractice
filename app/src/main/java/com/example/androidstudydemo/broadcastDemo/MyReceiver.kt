package com.example.androidstudydemo.broadcastDemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.dmoral.toasty.Toasty

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action:String? = intent?.action
        if(action=="传递广播数据"){
            val data:String? = intent?.getStringExtra("name")
            if (context != null) {
                Toasty.success(context, "静态广播收到：$data", Toasty.LENGTH_SHORT).show()
            }
        }
        else if(action=="android.intent.action.SCREEN_ON")
            Toasty.success(context!!, "屏幕打开", Toasty.LENGTH_SHORT).show()
        else if(action=="android.intent.action.SCREEN_OFF")
            Toasty.success(context!!, "屏幕关闭", Toasty.LENGTH_SHORT).show()
        else if(action=="android.intent.action.BOOT_COMPLETED")
            Toasty.success(context!!, "手机启动完成", Toasty.LENGTH_SHORT).show()
        else if(action=="android.intent.action.TIME_SET")
            Toasty.success(context!!, "时间设置完成", Toasty.LENGTH_SHORT).show()
    }


}