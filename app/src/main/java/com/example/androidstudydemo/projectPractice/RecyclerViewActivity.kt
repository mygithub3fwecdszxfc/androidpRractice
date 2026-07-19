package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {

    // Activity 的 ViewBinding
    private lateinit var binding: ActivityRecyclerViewBinding

    // 创建书籍适配器
    private val bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 允许内容延伸到系统状态栏和导航栏区域
        enableEdgeToEdge()

        // 加载 activity_recycler_view.xml
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 处理状态栏和底部导航栏的内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
                view,
                insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // 模拟书籍数据
// 模拟书籍列表测试数据，提供多本不同书名、作者的图书，统一使用默认封面资源
        val bookList = listOf(
            Book(
                title = "夜色将明",
                author = "书页拾光",
                coverResId = R.drawable.book
            ),
            Book(
                title = "风起长安",
                author = "墨香如故",
                coverResId = R.drawable.book
            ),
            Book(
                title = "星河入梦",
                author = "清风徐来",
                coverResId = R.drawable.book
            ),
            Book(
                title = "山海旧事",
                author = "南山客",
                coverResId = R.drawable.book
            ),
            Book(
                title = "云间寄信人",
                author = "晚舟归",
                coverResId = R.drawable.book
            ),
            Book(
                title = "青崖白鹿行",
                author = "温九辞",
                coverResId = R.drawable.book
            ),
            Book(
                title = "雾中灯塔",
                author = "林野",
                coverResId = R.drawable.book
            ),
            Book(
                title = "人间烟火录",
                author = "简安",
                coverResId = R.drawable.book
            ),
            Book(
                title = "月落枫桥",
                author = "苏枕书",
                coverResId = R.drawable.book
            ),
            Book(
                title = "孤岛来信",
                author = "顾淮",
                coverResId = R.drawable.book
            ),
            Book(
                title = "边城晚歌",
                author = "沈清和",
                coverResId = R.drawable.book
            ),
            Book(
                title = "拾光杂货铺",
                author = "知夏",
                coverResId = R.drawable.book
            ),
            Book(
                title = "渡川纪事",
                author = "砚秋",
                coverResId = R.drawable.book
            ),
            Book(
                title = "晚风叙旧",
                author = "江寻",
                coverResId = R.drawable.book
            ),
            Book(
                title = "山野游记",
                author = "白樵",
                coverResId = R.drawable.book
            )
        )
        // 设置网格布局管理器
        // 2 表示每一行显示两本书
        binding.bookRecyclerView.layoutManager =
            GridLayoutManager(this, 2)

        // 将适配器绑定给 RecyclerView
        binding.bookRecyclerView.adapter = bookAdapter

        // 将书籍数据交给适配器显示
        bookAdapter.updateBookList(bookList)
    }
}
