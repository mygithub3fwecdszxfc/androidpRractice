package com.example.androidstudydemo.tabLayoutDemo

import com.example.androidstudydemo.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidstudydemo.databinding.FragmentTabcontentBinding

class TabContentFragment : Fragment(){
    private lateinit var binding: FragmentTabcontentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {

        val title = arguments?.getString("title")
        binding = FragmentTabcontentBinding.inflate(inflater, container, false)
        binding.tvContent.text = title
        return binding.root

    }
    companion object {
        /**
         * 工厂方法，创建TabContentFragment实例，并传递页面标题数据
         * @param str 需要展示的页面文本
         * @return TabContentFragment 对象
         */
        fun newInstance(str: String): TabContentFragment {
            // 创建碎片实例
            val fragment = TabContentFragment()
            // Bundle 用于存放需要传递的数据
            val bundle = Bundle()
            // 将传入的字符串存入Bundle，key为title
            bundle.putString("title", str)
            // 将Bundle赋值给fragment的arguments，完成参数携带
            fragment.arguments = bundle
            return fragment
        }
    }

}