package com.example.androidstudydemo.recyclerViewDemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudydemo.databinding.NewsListBinding

/**
 * RecyclerView新闻列表适配器
 * 负责新闻条目视图的创建、复用、数据填充
 * @param list 新闻数据源集合，存放News实体数据
 */
class NewsAdapter(private val list: List<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /**
     * ViewHolder：视图缓存容器
     * 核心作用：缓存item内所有控件引用，避免频繁执行findViewById，实现视图复用优化
     * @param binding DataBinding自动生成的绑定类，对应布局 news_list.xml
     */
    class NewsViewHolder(val binding: NewsListBinding) : RecyclerView.ViewHolder(binding.root)

    /**
    创建ViewHolder
     * 触发时机：RecyclerView需要新增条目视图时调用，屏幕滑动不会反复执行（复用机制）
     * @param parent 当前item的父容器，也就是RecyclerView
     * @param viewType 条目类型，多布局列表场景用于区分不同样式item
     * @return 封装好布局的ViewHolder对象
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        // 通过DataBinding加载item布局news_list.xml，生成NewsListBinding
        val binding = NewsListBinding.inflate(
            LayoutInflater.from(parent.context), // 获取布局加载器
            parent,                              // 传入父容器
            false                                // false：不自动挂载到父布局，交给RecyclerView统一管理
        )
        return NewsViewHolder(binding)
    }

    /**
    获取列表总条目数量
     * RecyclerView依靠该方法确定列表数据范围
     * @return 数据源集合的长度
     */
    override fun getItemCount(): Int = list.size

    /**
    绑定数据到item视图
     * 触发时机：列表滑动时频繁调用，为复用的ViewHolder填充对应position的数据
     * @param holder 被复用的ViewHolder视图缓存
     * @param position 当前条目在数据源list中的索引下标
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        // 根据下标取出当前条目对应的新闻数据实体
        val newItem = list[position]

        // 给标题文本控件赋值
        holder.binding.titleTextView.text = newItem.title
        // 给描述文本控件赋值
        holder.binding.contentTextView.text = newItem.hint

        // Glide加载网络缩略图
        // with：绑定上下文
        // load：传入图片网络url
        // into：指定要展示图片的ImageView控件
        Glide
            .with(holder.itemView.context)
            .load(newItem.thumbnail)
            .into(holder.binding.newsImageView)
    }
}