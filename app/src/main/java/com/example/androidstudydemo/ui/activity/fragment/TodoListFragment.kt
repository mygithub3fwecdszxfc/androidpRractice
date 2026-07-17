package com.example.androidstudydemo.ui.activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.FragmentTodoListBinding

/**
 * 待办事项列表碎片
 * 依附于宿主Activity运行，用于展示待办清单页面
 * 单个Activity可承载多个Fragment，实现页面分块、复用
 */
class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding

    /**
     * Fragment生命周期回调：加载碎片布局
     * @param inflater 布局填充器，用来解析xml布局文件
     * @param container 宿主Activity中承载Fragment的父容器控件
     * @param savedInstanceState 页面销毁前保存的临时数据
     * @return 填充完成的碎片View视图
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

}