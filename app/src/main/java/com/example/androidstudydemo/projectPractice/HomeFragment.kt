package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.BannerItemBinding
import com.example.androidstudydemo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Calendar
import java.util.Random

/**
 * 首页Fragment
 * 包含书籍轮播Banner（ViewPager2实现），搭配圆点指示器TabLayout
 * 实现功能：Banner自动轮播、页面可见时滚动、页面不可见暂停滚动
 */
class HomeFragment : Fragment() {
    //初始化多条阅读摘录
    private val quoteList=listOf(
        "人只要一想到生活的美好，就会有继续生活下去的勇气。",
        "活在这珍贵的人间，太阳强烈，水波温柔。",
        "凡是过往，皆为序章。",
        "万物皆有裂痕，那是光照进来的地方。",
        "一定要爱着点什么，恰似草木对光阴的钟情。",
        "生活不可能像你想象得那么好，但也不会像你想象得那么糟。",
        "我们读所有的书，最终的目的都是读到自己。",
        "且视他人之疑目如盏盏鬼火，大胆去走你的夜路。",
        "允许自己做自己，允许别人做别人。",
        "山海自有归期，风雨自有相逢。",
        "对未来真正的慷慨，是把一切献给现在。",
        "不乱于心，不困于情，不畏将来，不念过往。",
        "世界上只有一种英雄主义，就是看清生活真相之后依然热爱生活。",
        "生命的意义，在于人与人的相互照亮。",
        "慢慢理解世界，慢慢更新自己。"
    )
    // 主线程Handler，用于实现定时自动轮播
    private val autoScrollHandler = Handler(Looper.getMainLooper())

    /**
     * 自动轮播执行任务
     */
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            // 至少2张图才需要轮播
            if (bannerBooks.size > 1) {
                // 计算下一页下标，取模实现循环回到第一页
                val nextPosition = (binding.vpBanner.currentItem + 1) % bannerBooks.size
                // 切换页面，true开启平滑滚动动画
                binding.vpBanner.setCurrentItem(nextPosition, true)
                // 3秒后再次执行自身，持续轮播
                autoScrollHandler.postDelayed(this, 3000L)
            }
        }
    }

    // 视图绑定对象
    private lateinit var binding: FragmentHomeBinding

    /**
     * Banner轮播条目ViewHolder
     * @param binding 条目布局绑定类
     */
    private class BannerViewHolder(
        val binding: BannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    // Banner轮播书籍模拟数据源
    private val bannerBooks = listOf(
        Book(
            title = "活着",
            author = "余华",
            coverUrl = "https://ts1.tc.mm.bing.net/th/id/OIP-C.HFvt9OHHj4jEXL_1aJDWFQHaHa?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
        ),
        Book(
            title = "月亮与六便士",
            author = "毛姆",
            coverUrl = "https://pic3.zhimg.com/v2-75665daea4374094209c9b5017869e92_b.jpg"
        ),
        Book(
            title = "人间草木",
            author = "汪曾祺",
            coverUrl = "https://img.alicdn.com/bao/uploaded/TB1twTPaXzqK1RjSZFCSuvbxVXa.jpg"
        )
    )

    private val newBooks = listOf(
        Book(
            title = "平凡的世界",
            author = "路遥",
            coverUrl = "https://tse3-mm.cn.bing.net/th/id/OIP-C.JXG86WC2yhEHZIKeX2ZwDgHaIJ?w=192&h=211&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3"
        ),
        Book(
            title = "围城",
            author = "钱钟书",
            coverUrl = "https://tse3-mm.cn.bing.net/th/id/OIP-C.cWgxmfsgaLfDbpKAAKUPBAHaKA?w=145&h=195&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3"
        ),
        Book(
            title = "小王子",
            author = "圣埃克苏佩里",
            coverUrl = "https://tse3-mm.cn.bing.net/th/id/OIP-C.WHFaK7e5VI5un1vuo9UqtwHaHa?w=220&h=220&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3"
        ),
        Book(
            title = "解忧杂货店",
            author = "东野圭吾",
            coverUrl = "https://tse4-mm.cn.bing.net/th/id/OIP-C.n6eTWQAnjk45GJ6hVBS6bgAAAA?w=152&h=218&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3"
        )
    )

    /**
     * 创建Fragment视图，加载布局
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用ViewBinding加载Fragment布局
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // 随机获取一条阅读摘录
        val randomQuote = quoteList.random()
        binding.tvDailyQuote.text = getDailyQuote()
        return binding.root
    }

    /**
     * 视图创建完成，初始化UI逻辑
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 给ViewPager2设置匿名内部适配器，实现Banner轮播页面渲染
        binding.vpBanner.adapter = object : RecyclerView.Adapter<BannerViewHolder>() {
            /**
             * 创建条目ViewHolder，加载BannerItem布局
             * @param parent RecyclerView父容器
             * @param viewType item类型，多布局场景使用，当前单布局无效
             */
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BannerViewHolder {
                // 使用ViewBinding加载单条Banner布局
                val itemBinding = BannerItemBinding.inflate(
                    LayoutInflater.from(parent.context), // 获取布局渲染器
                    parent,                               // item依附的父容器
                    false                                 // false：不自动addView，由RecyclerView管理
                )
//                页面首次显示时一定会走 onResume，统一在 onResume 启动，逻辑更统一，避免双重触发。
//                autoScrollHandler.postDelayed(autoScrollRunnable, 3000L)
                // 将binding传入ViewHolder缓存并返回
                return BannerViewHolder(itemBinding)
            }

            /**
             * 绑定对应位置的数据到Item控件
             * @param holder ViewHolder
             * @param position 当前条目索引
             */
            override fun onBindViewHolder(
                holder: BannerViewHolder,
                position: Int
            ) {
                val book = bannerBooks[position]
                // 设置书籍名称
                holder.binding.tvBannerBookName.text = book.title

                // Glide加载书籍封面图片
                Glide.with(holder.itemView.context)
                    .load(book.coverUrl)                // 图片网络地址
                    .placeholder(R.drawable.book)       // 加载中占位图
                    .error(R.drawable.book)             // 加载失败兜底图
                    .into(holder.binding.ivBannerImg)   // 目标ImageView
            }

            /**
             * 返回轮播条目总数量
             */
            override fun getItemCount(): Int {
                return bannerBooks.size
            }
        }

        // TabLayoutMediator绑定TabLayout圆点指示器 和 ViewPager2
        TabLayoutMediator(
            binding.tabDot,
            binding.vpBanner
        ) { tab, position ->
            tab.text = "" // 不显示文字，只保留圆点指示器
        }.attach()
//recyclerView设置
        binding.rvNewBooks.layoutManager = GridLayoutManager(
            requireContext(),
            2
        )
        binding.rvNewBooks.adapter = BookAdapter(newBooks)
    }

    /**
     * Fragment失去焦点、不可见时触发
     * 停止自动轮播，节省性能
     */
    override fun onPause() {
        super.onPause()
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    /**
     * Fragment重新可见、获得焦点时触发
     * 恢复自动轮播
     */
    override fun onResume() {
        super.onResume()
        // 先清掉残留任务，再新增，杜绝多个任务同时跑
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
        autoScrollHandler.postDelayed(autoScrollRunnable, 3000L)
    }

    /**
     * Fragment视图销毁，释放资源防止内存泄漏
     */
    override fun onDestroyView() {
        // 移除轮播任务，避免页面销毁后继续执行回调
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
        // 清空ViewPager适配器，解除引用
        binding.vpBanner.adapter = null
        super.onDestroyView()
    }


    private fun getDailyQuote(): String {
        // 获取系统日历实例，代表当前手机时间
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // timeInMillis = 从1970-01-01到当日零点的毫秒数，long类型，用作随机种子
        val seed = calendar.timeInMillis
        val random = Random(seed)
        val index = random.nextInt(quoteList.size)
        return quoteList[index]
    }
}
