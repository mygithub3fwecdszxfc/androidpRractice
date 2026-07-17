package com.example.androidstudydemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androidstudydemo.databinding.ActivityImageView1Binding

class ImageView1 : AppCompatActivity() {

    // activity_image_view1.xml 对应生成的 ViewBinding 类。
    // 有了 binding，就可以直接写 binding.image1、binding.image2、binding.image3，
    // 不需要再写 findViewById。
    private lateinit var binding: ActivityImageView1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 加载 activity_image_view1.xml 布局文件。
        binding = ActivityImageView1Binding.inflate(layoutInflater)

        // binding.root 表示 XML 最外层的根布局，也就是 LinearLayout。
        setContentView(binding.root)

        // 处理状态栏、导航栏占位，避免内容被系统栏遮住。
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // image1 和 image2 在 XML 里已经通过 android:src 显示了本地图片。
        // 这里演示：使用 Glide 把网络图片加载到第三个 ImageView 中。
        Glide.with(this)
            .load("https://tse3.mm.bing.net/th/id/OIP.LxWbwPZbkjERC3Su-St-bQHaFE?r=0&rs=1&pid=ImgDetMain&o=7&rm=3")
            .centerCrop()

            .into(binding.image3)
    }
}
