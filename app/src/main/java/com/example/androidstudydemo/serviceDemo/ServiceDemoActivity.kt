package com.example.androidstudydemo.serviceDemo

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityServiceDemoBinding
import es.dmoral.toasty.Toasty

class ServiceDemoActivity : AppCompatActivity() {
    private var myService: MyServiceDemo1? = null
    private val connection=object :ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val binder = service as MyServiceDemo1.MyBinder
            myService = binder.getService()
            Toasty.success(this@ServiceDemoActivity, "Service connected成功绑定", Toasty.LENGTH_SHORT).show()

        }
        //主动调用 unbindService () → 不会触发 onServiceDisconnected！
        //onServiceDisconnected 仅发生在：服务进程意外崩溃、被系统强行杀死，Binder 连接意外断开
        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            Toasty.error(this@ServiceDemoActivity, "Service disconnected解除绑定", Toasty.LENGTH_SHORT).show()
        }

    }

    private lateinit var binding: ActivityServiceDemoBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityServiceDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.start.setOnClickListener {
            // 构建意图，指定当前页面 → 目标服务 MyServiceDemo1
            val intent = Intent(this, MyServiceDemo1::class.java)
            // 启动普通后台服务
            startService(intent)
            Toasty.success(this@ServiceDemoActivity, "Service started启动服务", Toasty.LENGTH_SHORT).show()
        }
        binding.stop.setOnClickListener {
            val intent = Intent(this, MyServiceDemo1::class.java)
            // 停止服务
            stopService(intent)
            Toasty.success(this@ServiceDemoActivity, "Service stopped停止服务", Toasty.LENGTH_SHORT).show()
        }

        //bind模式启动service开始咯
        binding.bind.setOnClickListener {
            val intent = Intent(this, MyServiceDemo1::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
        binding.unbind.setOnClickListener {
            if(myService!=null){
            unbindService(connection)
            myService=null
                Toasty.success(this@ServiceDemoActivity, "Service unbind解除绑定", Toasty.LENGTH_SHORT).show()
            }
        }
        binding.getData.setOnClickListener {
            //也可以判空
            if(myService==null){
                Toasty.error(this@ServiceDemoActivity, "Service is null还没绑定", Toasty.LENGTH_SHORT).show()
            }
            else{
                val count = myService?.getCount()?:-1
                Toasty.success(this@ServiceDemoActivity, "Service getservice获取服务，count=$count", Toasty.LENGTH_SHORT).show()
            }
        }

    }

    fun back(view: View) {
        val intent =Intent(this, TestServiceActivity::class.java)
        startActivity(intent)
    }
   override fun onDestroy(){
       if(myService!=null) unbindService(connection)
        super.onDestroy()
    }
}