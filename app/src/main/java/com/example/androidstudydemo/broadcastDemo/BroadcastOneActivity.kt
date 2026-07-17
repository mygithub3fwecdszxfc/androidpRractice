package com.example.androidstudydemo.broadcastDemo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroadcastOneBinding
import es.dmoral.toasty.Toasty

/**
 * 广播接收页面
 * 动态注册广播接收器：接收 BroadcastTwoActivity 发出的自定义广播
 * 动态注册特点：页面打开注册，页面销毁必须解注册；页面退出后不再接收广播
 */
class BroadcastOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastOneBinding

    /**
     * 自定义广播接收器
     * BroadcastReceiver：广播接收核心类，系统收到对应Action广播后回调onReceive
     */
    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            if (action == "com.example.androidstudydemo.MY_BROADCAST") {
                val name: String? = intent.getStringExtra("name")
                binding.btnGoToB.text = name ?: "等待接收消息"
                Toasty.success(context.applicationContext, "接收到广播数据了Received: $name", Toasty.LENGTH_SHORT).show()
            }
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
                Toasty.success(context.applicationContext, "接收到网络状态变化广播", Toasty.LENGTH_SHORT).show()
                updateNetworkStatus()
            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(messageReceiver)
        unregisterReceiver(networkReceiver)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadcastOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        registerReceivers()

        // 跳转至广播发送页面
        binding.btnGoToB.setOnClickListener {
            val intent = Intent(this, BroadcastTwoActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerReceivers() {
//         ======= 动态注册广播接收器(页面间通信) =======
//         参数1：广播接收器对象
//         参数2：IntentFilter，设置要监听的广播Action
//         参数3：RECEIVER_NOT_EXPORTED 当前应用内广播，外部App无法调用，安全
        val filter = IntentFilter("com.example.androidstudydemo.MY_BROADCAST")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android13及以上 使用5参数重载，传入flag
            registerReceiver(messageReceiver, filter, RECEIVER_EXPORTED, )
            Toasty.success(this, "注册页面间广播接收器成功", Toasty.LENGTH_SHORT).show()
        } else {
            // Android12及以下 使用2参数重载，无导出标记参数
            registerReceiver(messageReceiver, filter)
        }

//        //动态注册广播，监听网络状态变化
//        val networkFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            // Android13及以上 使用5参数重载，传入flag
//            registerReceiver(networkReceiver, networkFilter, Context.RECEIVER_EXPORTED, )
//            Toasty.success(this, "注册系统广播接收器成功", Toasty.LENGTH_SHORT).show()
//        } else {
//            // Android12及以下 使用2参数重载，无导出标记参数
//            registerReceiver(networkReceiver, networkFilter)
//        }
    }

    private fun updateNetworkStatus(){
        //获取系统服务，connect.CONNECTIVITY_SERVICE表示网络连接服务
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //返回当前活跃的network对象
        val activeNetwork = connectivityManager.activeNetwork
        //获取网络的能力描述
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //判断网络是否可用
        val isConnected=networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ==true
        binding.tvNetworkStatus.text = if (isConnected) "网络已连接" else "网络未连接"
    }
}