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
    // 模拟书架数据。
    val bookList = listOf(
        Book("夜色将明", "书页拾光", "https://marketplace.canva.cn/EAGLR36NmLQ/1/0/501w/canva-3RTOYGe9baU.jpg"),
        Book("风起长安", "墨香如故", "https://iacg.cc/wp-content/uploads/2022/02/1c0deeeecb6764b1a954e6af69734d90.jpg"),
        Book("星河入梦", "清风徐来", "https://tse4.mm.bing.net/th/id/OIP.q8zYw9HcgPZxe8FZrTdInwHaKX?r=0&w=720&h=1008&rs=1&pid=ImgDetMain&o=7&rm=3"),
        Book("山海旧事", "南山客", "https://tse4.mm.bing.net/th/id/OIP.DAstaTXmmTdvMHmq5OKMkAHaJ4?r=0&w=750&h=1000&rs=1&pid=ImgDetMain&o=7&rm=3"),
        Book("云间寄信人", "晚舟归", "https://tse2.mm.bing.net/th/id/OIP.QU5T0zD2CxfdgLL5khJInAAAAA?r=0&w=214&h=285&rs=1&pid=ImgDetMain&o=7&rm=3"),
        Book("青崖白鹿行", "温九辞", "https://s.eslite.com/Upload/Product/201612/o/636161779281318750.jpg"),
        Book("雾中灯塔", "林野", "https://tse2.mm.bing.net/th/id/OIP.FXmglrTJIUO7LuVaMDqrAQHaJ4?r=0&pid=ImgDet&w=184&h=246&c=7&dpr=1.3&o=7&rm=3"),
        Book("人间烟火录", "简安", "https://tse3.mm.bing.net/th/id/OIP.KY9TMJR5dlE4_NdfLn7QxgAAAA?r=0&pid=ImgDet&w=151&h=202&c=7&dpr=1.3&o=7&rm=3")
    )
    private lateinit var binding: FragmentBookShelfBinding
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookShelfBinding.inflate(inflater, container, false)
        bookAdapter = BookAdapter(bookList)
        binding.bookRecyclerView.adapter = bookAdapter
        // 设置书架为两列网格布局。
        binding.bookRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)

        return binding.root
    }

}
