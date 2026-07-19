package com.example.androidstudydemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androidstudydemo.databinding.ActivityWeekTextInputBinding
import es.dmoral.toasty.Toasty

class WeekTextInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeekTextInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeekTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 输入内容变化时，重新检查账号并清除错误提示。
        binding.accountEdit.doAfterTextChanged {
            binding.accountLayout.error = checkAccount(it?.toString().orEmpty())
        }

        binding.passwordEdit.doAfterTextChanged {
            binding.passwordLayout.error =
                checkPassword(it?.toString().orEmpty())
        }
        binding.phoneEdit.doAfterTextChanged {
            binding.phoneLayout.error =
                checkPhone(it?.toString().orEmpty())
        }
        binding.loginButton.setOnClickListener {
            // 点击登录时再次统一校验所有输入框。
            val accountError = checkAccount(binding.accountEdit.text?.toString().orEmpty())
            val passwordError = checkPassword(binding.passwordEdit.text?.toString().orEmpty())
            val phoneError = checkPhone(binding.phoneEdit.text?.toString().orEmpty())

            binding.accountLayout.error = accountError
            binding.passwordLayout.error = passwordError
            binding.phoneLayout.error = phoneError

            if (accountError == null && passwordError == null && phoneError == null) {
                // 执行登录操作
                Toasty.success(this, "登录成功", Toast.LENGTH_SHORT).show()
            }
        }

        Glide.with(this)
            .load("https://tse4.mm.bing.net/th/id/OIP.F6etPtL62Y8sOeu1VH_vCgHaM9?r=0&rs=1&pid=ImgDetMain&o=7&rm=3")
            .placeholder(R.drawable.ic_person_24)
            .error(R.drawable.ic_search_24)
            .fitCenter()
            .into(binding.glideImage)


        Glide.with(this)
            .load("https://tse4.mm.bing.net/th/id/OIP.F6etPtL62Y8sOeu1VH_vCgHaM9?r=0&rs=1&pid=ImgDetMain&o=7&rm=3")
            .placeholder(R.drawable.ic_person_24)
            .error(R.drawable.ic_search_24)
            .centerCrop()
            .into(binding.glideImage2)
    }

    private fun checkAccount(str: String): String? {
        return if (str.isBlank()) {
            "请输入账号，不能为空哦"
        } else {
            null
        }
    }

    private fun checkPassword(str: String): String? {
        val hasLetter = str.any { it.isLetter() }
        val hasNumber = str.any { it.isDigit() }

        return when {
            str.isBlank() -> "请输入密码，不能为空哦"
            str.length < 6 -> "密码长度不能小于6"
            !str.all { it.isLetterOrDigit() } -> "密码只能包含字母和数字"
            !hasLetter -> "密码必须包含字母"
            !hasNumber -> "密码必须包含数字"
            else -> null
        }
    }

    private fun checkPhone(str: String): String? {
        return when {
            str.isBlank() -> "请输入手机号，不能为空哦"
            str.length != 11 -> "手机号长度必须为11位"
            !str.all { it.isDigit() } -> "手机号只能包含数字"
            else -> null
        }
    }

}
