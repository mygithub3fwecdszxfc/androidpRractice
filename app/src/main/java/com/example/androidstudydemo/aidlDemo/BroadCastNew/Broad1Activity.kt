package com.example.androidstudydemo.aidlDemo.BroadCastNew

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroad1Binding
import es.dmoral.toasty.Toasty


class Broad1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityBroad1Binding


    /**
     * 广播接收器
     */
    private val messageReceiver = object : BroadcastReceiver() {

        override fun onReceive(
            context: Context?,
            intent: Intent?
        ) {

            println("====Broad1收到广播来了====")

            val action = intent?.action

            if (action == "zzp发送广播了") {

                val message =
                    intent.getStringExtra("message")

                binding.tvText.text =
                    message ?: "没有收到消息"


                Toasty.success(
                    this@Broad1Activity,
                    "接收到广播: $message",
                    Toasty.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        binding =
            ActivityBroad1Binding.inflate(layoutInflater)

        setContentView(binding.root)



        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        // 注册广播
        registerReceiver()



        // 跳转发送广播页面
        binding.btnGoto.setOnClickListener {

            val intent =
                Intent(
                    this,
                    Broad2Activity::class.java
                )

            startActivity(intent)
        }
    }



    /**
     * 动态注册广播
     */
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerReceiver() {


        val filter =
            IntentFilter(
                "zzp发送广播了"
            )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


            registerReceiver(
                messageReceiver,
                filter,
                Context.RECEIVER_NOT_EXPORTED
            )


        } else {


            registerReceiver(
                messageReceiver,
                filter
            )

        }


        Toasty.success(
            this,
            "注册广播成功",
            Toasty.LENGTH_SHORT
        ).show()

    }



    /**
     * 页面销毁，注销广播
     */
    override fun onDestroy() {

        unregisterReceiver(
            messageReceiver
        )

        super.onDestroy()
    }

}