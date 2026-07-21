package com.example.androidstudydemo.projectPractice

// 书籍数据模型，代表书架中的一本书
data class Book(
    val title: String,       // 书名
    val author: String,      // 作者
    val coverUrl: String,     // 封面图片资源url
    // 【新增】书籍分类。提供默认值，保证原来没有传分类的 Book(...) 代码仍然可以编译。
    val category: String = "文学"
)
