package com.example.androidstudydemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.databinding.ActivityToastDemoBinding
import es.dmoral.toasty.Toasty

class ToastDemo : AppCompatActivity() {

    // activity_toast_demo.xml 生成的 ViewBinding 类，用它可以直接拿到 XML 里的控件
    private lateinit var binding: ActivityToastDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 加载布局，并把布局的根 View 设置成当前页面内容
        binding = ActivityToastDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 点击 XML 中 id 为 toast1 的按钮，弹出一个普通 Toast
        binding.toast1.setOnClickListener {
            Toast.makeText(this, "这是一个普通 Toast", Toast.LENGTH_SHORT).show()
            binding.toast1.text = "已经点击"
        }
        binding.toast2.setOnClickListener {
            Toasty.info(this, "这是一个信息高级 Toast", Toast.LENGTH_SHORT, true).show()
            binding.toast2.text = "已经点击"

            Toasty.success(this, "这是一个成功高级 Toast", Toast.LENGTH_SHORT, true).show()
        }
    }
}
