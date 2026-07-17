package com.example.androidstudydemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivitySplashBinding
import es.dmoral.toasty.Toasty

class SplashActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startAnimation()
        countDown()
    }

    private fun countDown() {
        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.count.text = (millisUntilFinished / 1000 + 1).toString()
                binding.count.apply{
                    scaleX = 0.6f
                    scaleY = 0.6f
                    animate()
                        .scaleX(1.5f)
                        .scaleY(1.5f)
                        .setDuration(900)
                        .start()
                }



            }

            override fun onFinish() {
//当前代码是在匿名内部类中，this指的是object而不是splashActivity，因此需要使用this@SplashActivity
                val intent = Intent(this@SplashActivity, LogininActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun startAnimation() {
        binding.font1.apply {
            alpha = 0f
            translationY = 500f
            scaleX = 0.8f
            scaleY = 0.8f
            rotation = 90f
            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(4000)
                .rotation(0f)
                .withEndAction {
                    animate()
                        .scaleX(1.5f)
                        .scaleY(1.5f)
                        .setDuration(2000)
                        .setInterpolator(BounceInterpolator())
                        .start()
                }
                .start()
        }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}
