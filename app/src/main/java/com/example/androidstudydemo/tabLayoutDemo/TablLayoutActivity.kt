package com.example.androidstudydemo.tabLayoutDemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityTablLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.dmoral.toasty.Toasty

class TablLayoutActivity : AppCompatActivity() {
    // ViewBinding视图绑定
    private lateinit var binding: ActivityTablLayoutBinding

    // Tab标题集合（5个标签页）
    private val tabTitles = listOf("热门", "体育", "科技", "生活", "娱乐" )

    // Tab图标集合（备用，当前注释未使用）
    private val tabIcons = listOf(R.drawable.home, R.drawable.news, R.drawable.person, R.drawable.sports, R.drawable.science,)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTablLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 适配系统状态栏、导航栏，防止UI被遮挡
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//
//        Log.d("打印日志", Thread.currentThread().name)
//        Thread{
//            Log.d("打印日志新开", Thread.currentThread().name)
//        }.start()

//        //添加tab方式一：循环逐个创建Tab、设置文字、添加进布局
//        for (title in tabTitles){
//            val tab = binding.tabLayout.newTab()
//            tab.text = title
//            binding.tabLayout.addTab(tab)
//        }

//        //添加tab方式二：链式写法，一行创建添加
//        for (title in tabTitles){
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title))
//        }
        //添加图标，使用了 TabLayoutMediator，它就会失效！
        tabTitles.forEachIndexed { index, string ->
            val tab = binding.tabLayout.newTab()
            tab.text = string
            tab.setIcon(tabIcons[index])
            binding.tabLayout.addTab(tab)

        }

        // TabLayout选中状态监听（三种状态：选中、取消、重选）
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // 新标签选中触发
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toasty.info(this@TablLayoutActivity, "当前选中了 ${tab.text}", Toast.LENGTH_SHORT).show()
            }

            // 旧标签取消选中触发（系统默认先取消旧的，再选中新的）
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Toasty.info(this@TablLayoutActivity, "当前取消了 ${tab.text}", Toast.LENGTH_SHORT).show()
            }

            // 同一个标签再次点击触发
            override fun onTabReselected(tab: TabLayout.Tab) {
                Toasty.info(this@TablLayoutActivity, "当前重新选中了 ${tab.text}", Toast.LENGTH_SHORT).show()
            }
        })

        // ViewPager2适配器：提供对应页面Fragment
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            // 返回对应位置的页面Fragment
            override fun createFragment(position: Int): Fragment = TabContentFragment.newInstance(tabTitles[position])
            // 页面总数 = Tab数量
            override fun getItemCount() = tabTitles.size
        }

        // 官方联动工具：TabLayout & ViewPager2 双向联动
        // 左右滑动页面自动切换Tab、点击Tab自动切换页面
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            tab.setIcon(tabIcons[position])
        }.attach()//启动 TabLayout 和 ViewPager2 的双向绑定联动。
}
}