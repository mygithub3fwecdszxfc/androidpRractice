package com.example.androidstudydemo.broadcastOrder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.dmoral.toasty.Toasty

class BroadcastOrder: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action:String? =intent?.action
        when(action){
            "无序广播" -> {
                // 处理无序广播

                Toasty.success(context!!,"无序广播",Toasty.LENGTH_SHORT).show()
            }
            "有序广播" -> {
                val bundle = getResultExtras(true)
                val name = bundle.getString("nickname")
                val lv = bundle.getInt("level")
                val win = bundle.getBoolean("isWin")
                Toasty.success(context!!,"有序广播1：$name 等级$lv，胜利：$win",Toasty.LENGTH_SHORT).show()
            }

        }
    }
}