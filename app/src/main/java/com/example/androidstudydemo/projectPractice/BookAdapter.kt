package com.example.androidstudydemo.projectPractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudydemo.databinding.ItemBookBinding

// RecyclerView 的适配器，负责创建和绑定书籍条目
class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // 保存当前需要显示的书籍数据
    private val bookList = mutableListOf<Book>()

    // 创建单个书籍条目的 ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        // 加载 item_book.xml 布局
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BookViewHolder(binding)
    }

    // 将指定位置的数据绑定到条目控件上
    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
        holder.bind(bookList[position])
    }

    // 返回 RecyclerView 需要显示的条目数量
    override fun getItemCount(): Int {
        return bookList.size
    }

    // ViewHolder 负责缓存单个条目中的控件
    class BookViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // 给当前书籍条目设置数据
        fun bind(book: Book) {
            binding.bookCover.setImageResource(book.coverResId)
            binding.tvBookTitle.text = book.title
            binding.tvBookAuthor.text = book.author
        }
    }

    // 更新书籍列表，并通知 RecyclerView 刷新
    fun updateBookList(newBookList: List<Book>) {
        bookList.clear()
        bookList.addAll(newBookList)

        // 通知 RecyclerView 数据发生变化
        notifyDataSetChanged()
    }
}