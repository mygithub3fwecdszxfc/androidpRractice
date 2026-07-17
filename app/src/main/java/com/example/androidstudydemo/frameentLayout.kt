package com.example.androidstudydemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// 这是 ViewBinding 自动生成的绑定类。
// 你的布局文件名是 activity_frameent_layout.xml，
// 所以生成的类名就是 ActivityFrameentLayoutBinding。
import com.example.androidstudydemo.databinding.ActivityFrameentLayoutBinding

class frameentLayout : AppCompatActivity() {

    // binding 用来代表 activity_frameent_layout.xml 这个布局文件。
    // 通过 binding 可以直接访问 XML 里有 id 的控件，不需要再写 findViewById。
    // lateinit 表示先声明变量，稍后在 onCreate() 里再初始化。
    private lateinit var binding: ActivityFrameentLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // inflate(layoutInflater) 会把 activity_frameent_layout.xml 加载成真正的 View 对象。
        // 加载完成后，XML 中有 id 的控件都会变成 binding 的属性。
        binding = ActivityFrameentLayoutBinding.inflate(layoutInflater)

        // binding.root 表示这个布局文件的根布局。
        // 这里等价于以前写 setContentView(R.layout.activity_frameent_layout)，
        // 但使用 ViewBinding 时要把 binding.root 设置给页面。
        setContentView(binding.root)

        // binding.main 对应 XML 中 android:id="@+id/main" 的控件。
        // 使用 ViewBinding 后，不需要写 findViewById(R.id.main)。
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // binding.fra 对应 XML 中 android:id="@+id/fra" 的 TextView。
        // 以前要写 findViewById<TextView>(R.id.fra)，现在可以直接 binding.fra。
        binding.fra.text = "zzp"
    }
}
