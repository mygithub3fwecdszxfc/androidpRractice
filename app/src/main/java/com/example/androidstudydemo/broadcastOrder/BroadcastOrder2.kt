package com.example.androidstudydemo.broadcastOrder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.dmoral.toasty.Toasty

class BroadcastOrder2: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action:String? =intent?.action
        when(action){
            "无序广播" -> {
                // 处理无序广播

                Toasty.success(context!!,"无序广播2",Toasty.LENGTH_SHORT).show()
            }
            "有序广播" -> {
                // 1. 获取发送方原始携带的数据
                val originText = intent.getStringExtra("name")
                Toasty.success(context!!,"【最高优先级】收到原始数据：$originText",Toasty.LENGTH_SHORT).show()

                // 2. 修改有序广播全局结果数据，下游接收器可以读取
                setResultData("这条数据被最高优先级BroadcastOrder2篡改了！！")

                // 可选：截断广播，后面接收器直接收不到
//                // abortBroadcast()
//                一次性同时设置 结果码、字符串、Bundle，功能全覆盖
//// code自定义状态码，data字符串，extras数据包
//                setResult(200, "全局返回文本", bundle)
//                读取配套：
//                kotlin
//                val code = resultCode // 获取状态码
//                val text = getResultData()
//                val bundle = getResultExtras(true)

            }

        }
    }
}