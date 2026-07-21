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
//        DecelerateInterpolator(1.5f)，效果是开始移动较快，接近终点时变慢，适合你的副标题淡入上移。
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
        //环形倒计时容器点击事件，点击直接跳过倒计时跳转登录
        binding.countDownContainer.setOnClickListener {
            countDownTimer?.cancel()
            jumpToLogin()
        }
        counDownJump()
    }

    private fun counDownJump() {
        //定义总倒计时时长5秒
        val totalMillis = 5000L
        //给环形进度条设置最大值为总毫秒数，初始进度拉满（完整圆环）
        binding.circularProgress.max = totalMillis.toInt()
        binding.circularProgress.setProgressCompat(totalMillis.toInt(), false)

        //CountDownTimer，高频刷新让圆环进度丝滑
        countDownTimer = object : CountDownTimer(totalMillis, 50) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished + 999) / 1000
                //更新中间倒计时数字
                binding.countDown.text = seconds.toString()
                //【新增】同步更新环形进度，第二个true开启平滑过渡动画
                binding.circularProgress.setProgressCompat(millisUntilFinished.toInt(), true)
            }

            override fun onFinish() {
                //【新增】倒计时结束，数字置0，圆环进度归零
                binding.countDown.text = "0"
                binding.circularProgress.setProgressCompat(0, true)
                jumpToLogin()
            }
        }.start()
    }

    //跳转登录方法，复用跳转逻辑
    private fun jumpToLogin(){
        Toasty.success(this@IntroduceActivity, "欢迎使用，即将为您跳转到登录页面", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@IntroduceActivity, MyLoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}