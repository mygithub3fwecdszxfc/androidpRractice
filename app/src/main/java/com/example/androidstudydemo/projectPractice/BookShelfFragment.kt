package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.databinding.FragmentBookShelfBinding

class BookShelfFragment : Fragment() {

    private lateinit var binding: FragmentBookShelfBinding
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookShelfBinding.inflate(inflater, container, false)

        // 【修改】书架页改为使用公共数据源，和分类页保持同一批书籍。
        bookAdapter = BookAdapter(BookRepository.allBooks)
        binding.bookRecyclerView.adapter = bookAdapter

        // 书架使用两列网格显示全部书籍。
        binding.bookRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )

        return binding.root
    }
}
