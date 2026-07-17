package com.example.androidstudydemo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnAttach
import androidx.core.widget.doOnTextChanged
import com.example.androidstudydemo.databinding.ActivityInputBinding

class input : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//     binding.phoneEdit.doOnTextChanged { text, start, before, count ->
//         if(text?.length!=11){
//             binding.phoneInput.error="手机号需满足11位!"
//         }else{
//             binding.phoneInput.error=null
//         }
//     } }

    binding.phoneEdit.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) {
                binding.phoneInput.error = null
            } else if (s.length != 11) {
                binding.phoneInput.error = "请输入11位手机号"
            } else {
                binding.phoneInput.error = null
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}

    }
