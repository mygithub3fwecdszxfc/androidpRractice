package com.example.androidstudydemo.projectPractice

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityIntroduceBinding
import es.dmoral.toasty.Toasty

class IntroduceActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private lateinit var binding: ActivityIntroduceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            //设置字体的另一种方式assets/fonts
//        val customFont = Typeface.createFromAsset(assets, "fonts/ZCOOLXiaoWei-Regular.ttf")
//        binding.tvText.typeface = customFont


///页面加载完成，等待 300ms
//        启动 2 秒过渡动画：
//        透明度从 0 → 1（淡入显现）
//        垂直位置从 +20dp 向上滑到 0dp（向上滑入）
//        动画结束后文字稳定正常显示,
//        推荐 DecelerateInterpolator(1.5f)，效果是开始移动较快，接近终点时变慢，适合你的副标题淡入上移。
        binding.tvSubtitle.post {
            binding.tvSubtitle.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(2000)
                .setInterpolator(DecelerateInterpolator(1.5f))
                .setStartDelay(300)
                .withEndAction {

                }
                .start()
        }

        counDownJump()
    }

    private fun counDownJump() {
       countDownTimer = object : CountDownTimer(5000, 1000) {
           override fun onTick(millisUntilFinished: Long) {
               val seconds = (millisUntilFinished + 999) / 1000
               binding.countDown.text = seconds.toString()
               binding.countDown.animate()
                   .scaleX(1.2f)
                   .scaleY(1.2f)
                   .setDuration(500)
                   .withEndAction {
                       binding.countDown.animate()
                           .scaleX(1f)
                           .scaleY(1f)
                           .setDuration(500)
                           .start()
                   }
                   .start()


           }

           override fun onFinish() {
               Toasty.success(this@IntroduceActivity, "欢迎使用，即将为您跳转到登录页面", Toast.LENGTH_SHORT).show()
               val intent = Intent(this@IntroduceActivity, MyLoginActivity::class.java)
               startActivity(intent)
               finish()
           }
        }.start()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}