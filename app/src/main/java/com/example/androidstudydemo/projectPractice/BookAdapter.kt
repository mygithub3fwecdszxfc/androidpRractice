package com.example.androidstudydemo.projectPractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ItemBookBinding

// RecyclerView 的适配器，负责创建和绑定书籍条目,private val bookList: List<Book> 构造参数
//外部页面（Activity/Fragment）创建 BookAdapter 时，把书籍数据列表传进来；
class BookAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

//bingding来源：在 onCreateViewHolder 中加载布局得到 binding，再传入 ViewHolder：
    //继承：把条目布局的根 View 交给父类，让 RecyclerView 识别这条目的视图，用于复用、绘制、回收。
    class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)
    // 创建单个书籍条目的 ViewHolder,return 的 BookViewHolder 对象，返回给 RecyclerView 底层框架。
    // 屏幕要显示新的列表条目，RecyclerView 先去复用池找旧的 ViewHolder；
    //池子里面没有可用缓存条目，就自动调用你写的 onCreateViewHolder()；
    //你代码里：加载布局生成 binding → 构造 BookViewHolder(binding) → return 这个 holder；
    //RecyclerView 拿到你返回的这个 holder：
    //把 holder 里的 item 视图添加到屏幕上；
    //同时把这个 holder 存入复用缓存池，后续滑动列表重复使用。
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

    // 将指定位置的数据绑定到条目控件上,绑定数据
    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
      val newBook = bookList[position]
        holder.binding.tvBookTitle.text = newBook.title
        holder.binding.tvBookAuthor.text = newBook.author
        Glide.with(holder.binding.bookCover.context)
            .load(newBook.coverUrl)
            .placeholder(R.drawable.book)
            .into(holder.binding.bookCover)

    }

    // 返回 RecyclerView 需要显示的条目数量
    override fun getItemCount(): Int {
        return bookList.size
    }


}