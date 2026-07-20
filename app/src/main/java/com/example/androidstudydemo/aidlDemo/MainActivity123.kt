package com.example.androidstudydemo.aidlDemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidstudydemo.R

class MainActivity123 : AppCompatActivity() {
    private val TAG = "AIDL客户端日志"
    private var remoteAidlProxy: IRemotePersonService? = null
    private lateinit var tvResult: TextView

    private val serviceConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "与远程独立进程服务连接成功")
            remoteAidlProxy = IRemotePersonService.Stub.asInterface(service)

            // 跨进程调用方法
            val sumResult = remoteAidlProxy?.add(10, 30)
            val person = remoteAidlProxy?.getPersonInfo()

            val showText = """
                跨进程加法结果：$sumResult
                远程返回人物：姓名=${person?.name}，年龄=${person?.age}
            """.trimIndent()
            tvResult.text = showText
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "远程服务断开连接")
            remoteAidlProxy = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main123)
        tvResult = findViewById(R.id.tv_result)
        // 页面创建时绑定远程AIDL服务
        bindRemoteAidlService()
    }

    private fun bindRemoteAidlService() {
        val intent = Intent(this, RemotePersonService::class.java)
        bindService(intent, serviceConn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConn)
    }
}