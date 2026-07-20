package com.example.androidstudydemo.projectPractice

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityContentProviderBinding
import es.dmoral.toasty.Toasty

class ContentProviderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentProviderBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 查询全部
        binding.btnQueryAll.setOnClickListener {
            val cursor = contentResolver.query(BookProvider.CONTENT_URI, null, null, null, null)
            val sb = StringBuilder()
            cursor?.use {
                while (it.moveToNext()) {
                    val title = it.getString(it.getColumnIndex("title"))
                    val author = it.getString(it.getColumnIndex("author"))
                    sb.appendLine("《$title》 - $author")
                }
            }
            binding.tvResult.text = if (sb.isEmpty()) "暂无数据" else sb.toString()
        }

        // 查询单条（修复：withAppendedPath）
        binding.btnQuerySingle.setOnClickListener {
            val uri = Uri.withAppendedPath(BookProvider.CONTENT_URI, "1")
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val title = it.getString(it.getColumnIndex("title"))
                    val author = it.getString(it.getColumnIndex("author"))
                    binding.tvResult.text = "《$title》 - $author"
                } else {
                    binding.tvResult.text = "未找到"
                }
            }
        }

        // 插入
        binding.btnInsert.setOnClickListener {
            val values = ContentValues().apply {
                put("title", "凡人修仙传")
                put("author", "忘语")
                put("coverUrl", "https://example.com/4.jpg")
            }
            contentResolver.insert(BookProvider.CONTENT_URI, values)
            Toasty.success(this, "插入成功", Toasty.LENGTH_SHORT).show()
        }

        // 更新
        binding.btnUpdate.setOnClickListener {
            val values = ContentValues().apply {
                put("title", "斗破苍穹（修订版）")
            }
            val uri = Uri.withAppendedPath(BookProvider.CONTENT_URI, "1")
            contentResolver.update(uri, values, null, null)
            Toasty.success(this, "更新成功", Toasty.LENGTH_SHORT).show()
        }

        // 删除
        binding.btnDelete.setOnClickListener {
            val uri = Uri.withAppendedPath(BookProvider.CONTENT_URI, "3")
            val deleted = contentResolver.delete(uri, null, null)
            Toasty.success(this, "删除了 $deleted 条", Toasty.LENGTH_SHORT).show()
        }
    }
}