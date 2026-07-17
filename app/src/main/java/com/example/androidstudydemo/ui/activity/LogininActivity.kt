package com.example.androidstudydemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityLogininBinding
import com.example.androidstudydemo.db.DatabaseHelper
import es.dmoral.toasty.Toasty

class LogininActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogininBinding
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogininBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setupListeners1()
        db = DatabaseHelper(this)
        binding.login1.setOnClickListener {
            login()
        }

    }
    private fun setupListeners1() {
        //监听用户名输入框 文本变化时清除错误提示
        binding.usernameInput.addTextChangedListener { text ->
            if (!text.isNullOrEmpty() && binding.usernameLayout.error != null) {
                binding.usernameLayout.error = null
            }
        }

        binding.passwordInput.addTextChangedListener { text ->
            if (!text.isNullOrEmpty() && binding.passwordLayout.error != null) {
                binding.passwordLayout.error = null
            }
        }

    }
    private fun login() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        // 非空校验
        if (username.isEmpty()) {
            binding.usernameLayout.error = "用户名不能为空"
            return
        }
        if (password.isEmpty()) {
            binding.passwordLayout.error = "密码不能为空"
            return
        }
        // 长度校验
        if (password.length < 6 || password.length > 20) {
            binding.passwordLayout.error = "密码长度在6-20个字符之间"
            return
        }

        val db = DatabaseHelper(this)
        val loginCode = db.login(username, password)
        when (loginCode) {
            0 -> binding.usernameLayout.error = "用户不存在"
            1 -> {
                Toasty.success(this, "登录成功，即将为你跳转到主页面").show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            2 -> binding.passwordLayout.error = "密码输入错误"
        }
    }

    fun rrrr(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}