package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.FragmentBookShelfBinding

class BookShelfFragment : Fragment() {
    private lateinit var binding: FragmentBookShelfBinding
    private val bookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookShelfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置书架为两列网格布局。
        binding.bookRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)

        // 将适配器绑定给 RecyclerView。
        binding.bookRecyclerView.adapter = bookAdapter

        // 模拟书架数据。
        val bookList = listOf(
            Book("夜色将明", "书页拾光", R.drawable.book),
            Book("风起长安", "墨香如故", R.drawable.book),
            Book("星河入梦", "清风徐来", R.drawable.book),
            Book("山海旧事", "南山客", R.drawable.book),
            Book("云间寄信人", "晚舟归", R.drawable.book),
            Book("青崖白鹿行", "温九辞", R.drawable.book),
            Book("雾中灯塔", "林野", R.drawable.book),
            Book("人间烟火录", "简安", R.drawable.book)
        )

        // 将数据交给适配器，RecyclerView 才会显示书籍。
        bookAdapter.updateBookList(bookList)
    }

}
