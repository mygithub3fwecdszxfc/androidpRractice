package com.example.androidstudydemo.threadDemo

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityThreadBinding
import es.dmoral.toasty.Toasty

class ThreadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThreadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun beginTime(view: View) {
        Thread {
            // 模拟耗时网络请求
            val strFromNet = getStringFromNet()
            // 子线程不能更新UI，切换主线程修改TextView
            runOnUiThread {
                binding.textView.text = strFromNet
            }
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()

        Toasty.success(this, "我不管你，我接着执行我的任务", Toasty.LENGTH_SHORT).show()
    }

    /**
     * 模拟网络耗时任务
     */
    private fun getStringFromNet(): String {
        // 模拟网络延迟
        Thread.sleep(3000)
        return "来自网络获取的数据"
    }
}