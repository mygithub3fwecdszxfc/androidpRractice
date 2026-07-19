package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityMyTabLayoutBinding
import com.example.androidstudydemo.tabLayoutDemo.TabContentFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.dmoral.toasty.Toasty

class MyTabLayoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyTabLayoutBinding
    private var tabTitles=listOf("关注","动态","收藏","生活","饮食")
    private var iconsList = listOf(R.drawable.guanzhu, R.drawable.dongtai, R.drawable.shoucang, R.drawable.life, R.drawable.food)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        //方式一
//        for(title in tabTitles){
//              val tab =binding.tabLayout2.newTab()
//            tab.text=title
//            binding.tabLayout2.addTab(tab)
//        }
//        //方式二
//        tabTitles.forEach { title ->
//            val tab = binding.tabLayout2.newTab()
//            tab.text = title
//            binding.tabLayout2.addTab(tab)
//        }

//        //方式三（带图标setIcon
//        tabTitles.forEachIndexed { index, string ->
//            val tab = binding.tabLayout2.newTab()
//            tab.text = string
//            tab.setIcon(iconsList[index])
//            binding.tabLayout2.addTab(tab)
//        }
        //tabLayout事件监听
        binding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                  Toasty.info(this@MyTabLayoutActivity, "选中 ${tab.text}", Toast.LENGTH_SHORT).show()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Toasty.info(this@MyTabLayoutActivity, "取消选中 ${tab.text}", Toast.LENGTH_SHORT).show()
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Toasty.info(this@MyTabLayoutActivity, "重复选中 ${tab.text}", Toast.LENGTH_SHORT).show()
            }
        })
        //设置viewPager2适配器FragmentStateAdapter
        binding.myViewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return TabFragment.newInstance(tabTitles[position])
            }

            override fun getItemCount(): Int {
                return tabTitles.size
            }

        }

        //viewPager2与tabLayout关联
        TabLayoutMediator(binding.tabLayout2, binding.myViewPager2) { tab, position ->
            tab.text = tabTitles[position]
            //也可以设置图标
            tab.setIcon(iconsList[position])
        }.attach()
    }
}