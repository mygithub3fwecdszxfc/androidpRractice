package com.example.androidstudydemo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyButton : AppCompatActivity(), View.OnClickListener {

    // 这个 TextView 用来显示“哪个按钮被点击了”。
    // 第三种方式的 onClick() 方法里也要用到它，所以把它定义成成员变量。
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_button)

        // 先找到结果文字控件，后面三种点击方式都会修改它的文字。
        resultText = findViewById(R.id.clickResultText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 方式二：在 Kotlin 代码里给按钮设置 setOnClickListener。
        // XML 里只需要给 button2 设置 id，不需要写 android:onClick。
        val secondButton = findViewById<Button>(R.id.button2)
        secondButton.setOnClickListener {
            resultText.text = "方式二：setOnClickListener 被点击"
        }

        // 方式三：让当前 Activity 实现 View.OnClickListener。
        // 多个按钮都把监听器设置成 this，点击后统一进入下面的 onClick() 方法。
        val thirdLoginButton = findViewById<Button>(R.id.button3)
        val thirdRegisterButton = findViewById<Button>(R.id.button4)

        thirdLoginButton.setOnClickListener(this)
        thirdRegisterButton.setOnClickListener(this)
    }

    // 方式一：通过 XML 的 android:onClick 属性绑定点击事件。
    // activity_my_button.xml 中 button1 写了 android:onClick="onButtonClick"。
    // 所以点击 button1 时，系统会自动调用这个同名方法。
    fun onButtonClick(view: View) {
        resultText.text = "方式一：XML android:onClick 被点击"
    }

    // 方式三的统一点击入口。
    // button3 和 button4 都设置了 setOnClickListener(this)，所以它们被点击后会来到这里。
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button3 -> {
                resultText.text = "方式三：登录zzp 被点击"
            }

            R.id.button4 -> {
                resultText.text = "方式三：注册zzp 被点击"
            }
        }
    }
}
