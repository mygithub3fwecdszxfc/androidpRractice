package com.example.androidstudydemo.projectPractice

// 书籍数据模型，代表书架中的一本书
data class Book(
    val title: String,       // 书名
    val author: String,      // 作者
    val coverResId: Int      // 封面图片资源 ID
)