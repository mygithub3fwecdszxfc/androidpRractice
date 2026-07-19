package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }


}