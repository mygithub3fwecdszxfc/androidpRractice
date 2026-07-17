package com.example.androidstudydemo.ui.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidstudydemo.databinding.FragmentNewsBinding
import androidx.fragment.app.Fragment

class NewsFragment: Fragment(){
    //这里不是viewBinding，是dataBinding
    private lateinit var binding:FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

}