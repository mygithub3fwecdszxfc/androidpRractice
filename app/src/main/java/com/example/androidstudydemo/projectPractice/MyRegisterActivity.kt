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
import com.example.androidstudydemo.databinding.ActivityMyRegisterBinding
import es.dmoral.toasty.Toasty

class MyRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 输入内容变化时，及时更新账号错误提示。
        binding.accountEdit.doAfterTextChanged { text ->
            binding.accountLayout.error = checkAccount(text?.toString().orEmpty())
        }

        // 密码变化时，同时重新检查确认密码是否仍然一致。
        binding.passwordEdit.doAfterTextChanged { text ->
            binding.passwordLayout.error = checkPassword(text?.toString().orEmpty())
            binding.repasswordLayout.error = checkRePassword(
                binding.passwordEdit.text?.toString().orEmpty(),
                binding.repasswordEdit.text?.toString().orEmpty()
            )
        }

        // 确认密码变化时，与密码输入框的内容进行比较。
        binding.repasswordEdit.doAfterTextChanged { text ->
            binding.repasswordLayout.error = checkRePassword(
                binding.passwordEdit.text?.toString().orEmpty(),
                text?.toString().orEmpty()
            )
        }

        binding.btnRegister.setOnClickListener {
            val account = binding.accountEdit.text?.toString()?.trim().orEmpty()
            val password = binding.passwordEdit.text?.toString().orEmpty()
            val rePassword = binding.repasswordEdit.text?.toString().orEmpty()

            val accountError = checkAccount(account)
            val passwordError = checkPassword(password)
            val rePasswordError = checkRePassword(password, rePassword)

            binding.accountLayout.error = accountError
            binding.passwordLayout.error = passwordError
            binding.repasswordLayout.error = rePasswordError

            // 三项校验都通过后，才提示模拟注册成功。
            if (accountError == null &&
                passwordError == null &&
                rePasswordError == null
            ) {
                Toasty.success(this, "模拟注册成功", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, MyLoginActivity::class.java))
            finish()
        }
    }

    private fun checkAccount(account: String): String? {
        return when {
            account.isBlank() -> "请输入账号，不能为空哦"
            account.length < 6 -> "账号长度不能小于6"
            account.length > 12 -> "账号长度不能大于12"
            else -> null
        }
    }

    private fun checkPassword(password: String): String? {
        val hasLetter = password.any { it.isLetter() }
        val hasNumber = password.any { it.isDigit() }

        return when {
            password.isBlank() -> "请输入密码，不能为空哦"
            password.length < 6 -> "密码长度不能小于6"
            !password.all { it.isLetterOrDigit() } -> "密码只能包含字母和数字"
            !hasLetter -> "密码必须包含字母"
            !hasNumber -> "密码必须包含数字"
            else -> null
        }
    }

    private fun checkRePassword(password: String, rePassword: String): String? {
        return when {
            rePassword.isBlank() -> "请确认密码"
            password != rePassword -> "两次输入的密码不一致"
            else -> null
        }
    }
}
