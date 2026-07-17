package com.example.androidstudydemo.aidlDemo.BroadCastNew

import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityBroad2Binding
import es.dmoral.toasty.Toasty


class Broad2Activity : AppCompatActivity() {


    private lateinit var binding: ActivityBroad2Binding



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        enableEdgeToEdge()


        binding =
            ActivityBroad2Binding.inflate(layoutInflater)


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



        /**
         * 点击按钮发送广播
         */
        binding.send.setOnClickListener {


            val intent =
                Intent(
                    "zzp发送广播了"
                ).apply {


                    // 当前应用内部广播
                    setPackage(packageName)


                    putExtra(
                        "message",
                        "hello你好世界!!!!!"
                    )
                }



            sendBroadcast(intent)



            Toasty.success(
                this,
                "广播已发送",
                Toasty.LENGTH_SHORT
            ).show()

        }

    }




    /**
     * 返回上一页
     */
    fun back(view: View) {

        finish()

    }

}