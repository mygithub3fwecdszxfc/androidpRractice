package com.example.androidstudydemo.projectPractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {
    private lateinit var binding: FragmentPersonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }


}