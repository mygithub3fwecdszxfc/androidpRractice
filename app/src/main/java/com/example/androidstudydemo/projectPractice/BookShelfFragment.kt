package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.databinding.FragmentBookShelfBinding
import es.dmoral.toasty.Toasty

class BookShelfFragment : Fragment() {

    private lateinit var binding: FragmentBookShelfBinding
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookShelfBinding.inflate(inflater, container, false)

        bookAdapter = BookAdapter(BookRepository.allBooks)
        binding.bookRecyclerView.adapter = bookAdapter

        // 书架使用两列网格显示全部书籍。
        binding.bookRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                // 获取输入关键词执行搜索
                val keyword = binding.etSearch.text.toString()
                searchBook(keyword)
                // 返回true：消费本次事件，不再默认执行收起键盘以外操作
                true
            } else {
                false
            }
        }


        return binding.root
    }
//模糊搜索

    private fun searchBook(query: String){
        // 多余空格
        val realQuery = query.trim()
        // 根据条件筛选书籍
        val filterBook = BookRepository.allBooks.filter {
            it.title.contains(realQuery) || it.author.contains(realQuery)
        }

        if (filterBook.isNotEmpty()) {
            // 存在匹配书籍，更新列表展示筛选结果
            bookAdapter.updateBooks(filterBook)
        } else {
            // 没有找到任何匹配书籍，弹出提示
            Toasty.warning(requireContext(), "未找到相关书籍", Toasty.LENGTH_SHORT).show()
            // 恢复显示全部书籍
            bookAdapter.updateBooks(BookRepository.allBooks)
        }
    }
}
