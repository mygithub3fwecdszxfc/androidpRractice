package com.example.androidstudydemo.broadcastOrder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.dmoral.toasty.Toasty

class BroadcastOrder1: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action:String? =intent?.action
        when(action){
            "无序广播" -> {
                // 处理无序广播

                Toasty.success(context!!,"无序广播1",Toasty.LENGTH_SHORT).show()
            }
            "有序广播" -> {
                // 获取上一级最高优先级接收器修改后的结果
                val modifyMsg = getResultData()
                Toasty.success(context!!,"有序广播1 收到修改后数据：$modifyMsg",Toasty.LENGTH_SHORT).show()
                // 创建Bundle，存放多条数据
                val bundle = Bundle()
                bundle.putString("nickname", "昭爷")
                bundle.putInt("level", 999)
                bundle.putBoolean("isWin", true)
                // 把Bundle塞给有序广播结果
                setResultExtras(bundle)
            }

        }
    }
}