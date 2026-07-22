package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.databinding.FragmentCategoryBinding

/**
 * 分类页面 Fragment
 * 功能：顶部Tab分类切换 + 下方RecyclerView筛选展示对应书籍
 */
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var bookAdapter: BookAdapter

    // 定义页面所有分类标签文本
    private val categories = listOf(
        "全部",
        "文学",
        "历史",
        "科幻",
        "生活",
        "外国文学"
    )

    // Fragment创建视图，加载布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化适配器：默认加载所有书籍
        bookAdapter = BookAdapter(BookRepository.allBooks)

        // 设置RecyclerView为2列网格布局
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        // 绑定适配器，列表开始渲染
        binding.categoryRecyclerView.adapter = bookAdapter

        // 循环遍历集合，动态给TabLayout添加每一个分类标签
        categories.forEach { category ->
            binding.categoryTabs.addTab(
                binding.categoryTabs.newTab().setText(category)
            )
        }

        // Tab选中监听事件
        binding.categoryTabs.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {

            // 选中新标签时触发：筛选对应分类书籍
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                filterBooks(categories[tab.position])
            }

            // 标签取消选中
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}

            // 重复点击当前标签，重新刷新筛选
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                filterBooks(categories[tab.position])
            }
        })

        // 默认选中全部
        val firstTab = binding.categoryTabs.getTabAt(0)
        if (firstTab != null) {
            firstTab.select()
        }
    }

    /**
     * 书籍筛选核心方法
     * 根据传入分类，过滤出对应书籍列表
     */
    private fun filterBooks(category: String) {
        val filteredBooks = if (category == "全部") {
            // 全部分类：返回原始所有书籍
            BookRepository.allBooks
        } else {
            //精准匹配筛选
            BookRepository.allBooks.filter { book ->
                book.category == category
            }
        }
        // 更新适配器数据，刷新RecyclerView列表
        bookAdapter.updateBooks(filteredBooks)
    }
}