
package com.example.androidstudydemo.projectPractice
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityServiceBinding
import es.dmoral.toasty.Toasty

class ServiceActivity : AppCompatActivity() {
    private var myService: MyService? = null
    private var isBound = false
    // ServiceConnection是绑定服务的连接对象,用于处理activity与service绑定状态变化
    private val connection = object : ServiceConnection {
        // 绑定成功后，系统把Service的Binder传给这里
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 强转成我们自定义的MyBinder
            val myBinder = service as MyService.MyBinder
            // 通过binder拿到Service实例，就能调用服务里的方法
            myService = myBinder.service
            isBound = true
            Toasty.success(this@ServiceActivity, "服务绑定成功", Toast.LENGTH_SHORT).show()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            Toasty.error(this@ServiceActivity, "服务异常失败", Toast.LENGTH_SHORT).show()
            // 服务意外断开，重置引用防止空指针
            myService = null
            isBound = false
        }
    }

    private lateinit var binding: ActivityServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.start.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }
        binding.stop.setOnClickListener {
            // 逻辑：如果已经绑定服务，禁止直接stopService
            if (isBound) {
                Toasty.error(this@ServiceActivity, "请先解绑服务，再停止", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 终止点击事件，不执行stop
            }
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }
        //如果不写这个标记，服务不存在时绑定会直接失败。
        binding.bind.setOnClickListener {
            // 防止重复绑定
            if (isBound) {
                Toasty.info(this@ServiceActivity, "已绑定，无需重复操作", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)
            // 删掉原来这里错误的 isBound = true，绑定成功标识统一在onServiceConnected赋值
        }
        binding.unbind.setOnClickListener {
            // 防止未绑定状态重复解绑崩溃
            if (!isBound) {
                Toasty.info(this@ServiceActivity, "当前未绑定服务", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            unbindService(connection)
            isBound = false
            myService = null
            Toasty.success(this@ServiceActivity, "服务解绑成功", Toast.LENGTH_SHORT).show()
        }
        binding.getData.setOnClickListener {
            // 增加判空，未绑定时禁止获取数据
            if (myService == null) {
                Toasty.error(this@ServiceActivity, "请先绑定服务", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toasty.success(this@ServiceActivity, "服务返回数据：${myService?.getCount()}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 页面销毁时解绑，避免内存泄漏
        if (isBound) {
            unbindService(connection)
            isBound = false
            myService = null
        }
    }

}
