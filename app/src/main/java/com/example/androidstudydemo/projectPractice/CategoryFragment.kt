package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var bookAdapter: BookAdapter

    // 【新增】Tab 中要显示的分类名称。
    // 这些文字必须和 BookRepository 中 Book.category 的值保持一致。
    private val categories = listOf(
        "全部",
        "文学",
        "历史",
        "科幻",
        "生活"
    )

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

        // 【新增】分类结果使用现有 BookAdapter，两列显示书籍封面、书名和作者。
        bookAdapter = BookAdapter(BookRepository.allBooks)
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )
        binding.categoryRecyclerView.adapter = bookAdapter

        // 【新增】把分类名称逐个添加到 TabLayout。
        categories.forEach { category ->
            binding.categoryTabs.addTab(
                binding.categoryTabs.newTab().setText(category)
            )
        }

        // 【新增】监听分类切换事件。
        binding.categoryTabs.addOnTabSelectedListener(
            object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                    filterBooks(categories[tab.position])
                }

                override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                    // 分类取消选中时不需要额外处理。
                }

                override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                    // 重复点击当前分类时重新筛选一次，保证列表状态正确。
                    filterBooks(categories[tab.position])
                }
            }
        )

        // 【新增】默认选中“全部”，首次进入页面时显示所有书籍。
        binding.categoryTabs.getTabAt(0)?.select()
    }

    /**
     * 【新增】根据分类名称筛选书籍。
     * “全部”是特殊选项，直接返回所有书籍；其他分类通过 category 字段匹配。
     */
    private fun filterBooks(category: String) {
        val filteredBooks = if (category == "全部") {
            BookRepository.allBooks
        } else {
            BookRepository.allBooks.filter { book ->
                book.category == category
            }
        }

        // 把筛选结果交给适配器，RecyclerView 会刷新为新的书籍列表。
        bookAdapter.updateBooks(filteredBooks)
    }
}
