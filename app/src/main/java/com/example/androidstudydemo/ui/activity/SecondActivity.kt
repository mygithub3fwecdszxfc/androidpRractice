package com.example.androidstudydemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val key = intent.getStringExtra("key")?:""
        val username = intent.getStringExtra("username")?:""
        val password = intent.getStringExtra("password")?:""
        binding.textView111.text = "Key: $key, Username: $username, Password: $password"

        val person1 = IntentCompat.getParcelableExtra(intent,"person1", Person1::class.java) as? Person1
        binding.textView112.text = "Person1: ${person1?.name}, ${person1?.age}, ${person1?.sex}"
    }

    fun onBackClick(view: View) {
        val intent= Intent(this, FirstActivity::class.java)
        startActivity(intent)

    }

    fun onBackWithDataClick(view: View) {
        val intent=Intent().apply {
            putExtra("result_key","zzpshangbanl")
            putExtra("result_key2","zzpshangbanl2")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onBackWithDataClick1(view: View) {
        val intent=Intent().apply {
            putExtra("result_key","新方式返回从数据")

        }
        setResult(RESULT_OK, intent)
        finish()
    }

}