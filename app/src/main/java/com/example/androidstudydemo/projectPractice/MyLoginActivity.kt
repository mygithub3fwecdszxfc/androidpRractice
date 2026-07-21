package com.example.androidstudydemo.projectPractice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityMyLoginBinding
import es.dmoral.toasty.Toasty

class MyLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 输入账号后，重新检查账号是否为空。
        binding.accountEdit.doAfterTextChanged { text ->
            binding.accountLayout.error = checkAccount(text?.toString().orEmpty())
        }

        // 输入密码后，重新检查密码是否为空。
        binding.passwordEdit.doAfterTextChanged { text ->
            binding.passwordLayout.error = checkPassword(text?.toString().orEmpty())
        }

        binding.btnLogin.setOnClickListener {
            val account = binding.accountEdit.text?.toString()?.trim().orEmpty()
            val password = binding.passwordEdit.text?.toString().orEmpty()

            val accountError = checkAccount(account)
            val passwordError = checkPassword(password)

            binding.accountLayout.error = accountError
            binding.passwordLayout.error = passwordError

            // 判断是否勾选协议
            if(!binding.cbAgree.isChecked){
                Toasty.info(this, "请先同意用户协议与隐私政策", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // 账号和密码都不为空后，执行模拟登录。
            if (accountError == null && passwordError == null) {
                Toasty.success(this, "模拟登录成功，跳转到主页", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, MyRegisterActivity::class.java))
            finish()
        }

        // 初始化：页面打开按钮禁用
        binding.btnLogin.isEnabled = false
        // 监听复选框勾选状态
        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnLogin.isEnabled = isChecked
            // 切换按钮样式，禁用时变淡
            if(isChecked){
                binding.btnLogin.alpha = 1f
            }else{
                binding.btnLogin.alpha = 0.5f
            }
        }
    }

    private fun checkAccount(account: String): String? {
        return if (account.isBlank()) {
            "请输入账号，不能为空哦"
        } else {
            null
        }
    }

    private fun checkPassword(password: String): String? {
        return if (password.isBlank()) {
            "请输入密码，不能为空哦"
        } else {
            null
        }
    }
}
