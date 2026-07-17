package com.example.androidstudydemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.postDelayed
import com.example.androidstudydemo.databinding.ActivityThreadDemoBinding
import es.dmoral.toasty.Toasty


class ThreadDemoActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityThreadDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThreadDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun method2(view: View) {
        Toasty.info(this, "按钮2被点击了", Toasty.LENGTH_SHORT).show()
    }
    fun method1(view: View) {
        Thread.sleep(10000)
        Toasty.info(this, "按钮1被点击了", Toasty.LENGTH_SHORT).show()
    }

    fun method3(view: View) {
//        val btn=view as Button
//        btn.text="按钮4"
        Thread{
            val btn=view as Button
            //runOnUiThread
            view.postDelayed(3000) {  btn.text="按钮4" }
//            btn.text="按钮4" // ❌ 子线程更新UI，抛出 CalledFromWrongThreadException
        }.start()
    }

    fun method4(view: View) {
        Thread{
            val btn=view as Button
            handler.post { btn.text="按钮4 helloworld" }
        }.start()
    }

}