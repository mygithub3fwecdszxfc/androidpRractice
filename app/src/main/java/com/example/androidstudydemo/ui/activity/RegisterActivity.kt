package com.example.androidstudydemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityRegisterBinding
import com.example.androidstudydemo.db.DatabaseHelper
import es.dmoral.toasty.Toasty

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setupListeners()
        db = DatabaseHelper(this)

        binding.register1.setOnClickListener {
            register()
        }

    }

    private fun setupListeners() {
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
        binding.repasswordInput.doAfterTextChanged { text ->
            if (!text.isNullOrEmpty() && binding.repasswordLayout.error != null) {
                binding.repasswordLayout.error = null
            }
        }
    }

    private fun register() {
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val repass = binding.repasswordInput.text.toString()

        // 点击注册时统一校验表单，判空、校验两次密码一致性
        // 缺陷：仅点击按钮时才会生成错误提示，输入框文字变更时不会自动清除报错
        // 因此需要配合文本监听，输入内容时实时清空错误提示
        when {
            username.isEmpty() -> binding.usernameLayout.error = "用户名不能为空"
            password.isEmpty() -> binding.passwordLayout.error = "密码不能为空"
            repass.isEmpty() -> binding.repasswordLayout.error = "确认密码不能为空"
            password != repass -> binding.repasswordLayout.error = "两次输入的密码不一致"
            //password密码长度必须在8-12位
            password.length < 8 || password.length > 12 -> binding.passwordLayout.error =
                "密码长度必须在8-12位"
            //password密码必须包含数字和字母
            password.none { it.isDigit() } || password.none { it.isLetter() } -> binding.passwordLayout.error =
                "密码必须包含数字和字母"
            //检测用户是否存在
            db.findUsername(username) -> binding.usernameLayout.error = "用户已存在!!!!!!"
            else -> {
                // 表单校验通过，执行注册逻辑
                val result = db.registerUser(username, password)
                if (result) {
                    Toasty.success(this, "注册成功,即将为您跳转到登录界面！", Toasty.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, LogininActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toasty.error(this, "注册失败", Toasty.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun llll(view: View) {
        val intent = Intent(this, LogininActivity::class.java)
        startActivity(intent)
        finish()
    }
}
