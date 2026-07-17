package com.example.androidstudydemo.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityMainBinding
import com.example.androidstudydemo.ui.activity.fragment.NewsFragment
import com.example.androidstudydemo.ui.activity.fragment.ProfileFragment
import com.example.androidstudydemo.ui.activity.fragment.TodoListFragment
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentList:List<Fragment>
    private var currentFragment: Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 绑定三个Fragment顺序必须和底部菜单顺序一致！
        fragmentList = listOf(
            TodoListFragment(),
            NewsFragment(),
            ProfileFragment()
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ========== 修复后的导航切换监听 ==========
        binding.naView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(fragmentList[0])
                    Toasty.info(this, "首页首页首页!!!!!!!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_news -> {
                    showFragment(fragmentList[1])
                    Toasty.info(this, "咨询咨询咨询咨询!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_profile -> {
                    showFragment(fragmentList[2])
                    Toasty.info(this, "个人中心个人中心个人中心!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // 默认显示首页
        showFragment(fragmentList[0])
    }

    // 隐藏/显示 模式（不会重复创建Fragment，性能最好）
    private fun showFragment(fragment:Fragment){
        //        // 方式一 replace 模式，会将上一个 Fragment 替换掉

//        val transaction =supportFragmentManager.beginTransaction ()

//        transaction.replace (R.id.fragment_container, fragment)

//        transaction.commit ()
        //方式二hide
        val transaction = supportFragmentManager.beginTransaction()
        if(!fragment.isAdded){
            transaction.add(R.id.fragment_container, fragment)
        }
        currentFragment?.let{
            transaction.hide(it)
        }
        transaction.show(fragment)
        transaction.commit()
        currentFragment = fragment
    }

}