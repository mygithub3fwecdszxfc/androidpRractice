package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.FragmentTabBinding


class TabFragment : Fragment() {

    private lateinit var binding: FragmentTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentTabBinding.inflate(inflater, container, false)
        // 通过arguments获取Bundle数据包中的参数
        binding.tvContent.text = arguments?.getString("title")

        return binding.root
    }

    companion object {
        fun newInstance(title:String): TabFragment {
            // 1. 创建Bundle数据包，用来存放需要传递给Fragment的参数
            val args = Bundle()
            // 2. 将下标position存入Bundle，key为"title"
            args.putString("title", title)
            // 3. 实例化空TabFragment对象
            val fragment = TabFragment()
            // 4. 把Bundle参数绑定给Fragment的arguments，页面重建时系统会自动保存恢复
            fragment.arguments = args
            // 5. 返回携带参数的Fragment实例供外部使用
            return fragment
        }
    }


}