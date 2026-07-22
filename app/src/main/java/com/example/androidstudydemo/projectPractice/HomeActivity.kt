package com.example.androidstudydemo.projectPractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityHomeBinding
import es.dmoral.toasty.Toasty

class HomeActivity : AppCompatActivity() {

    private lateinit var fragments:List<Fragment>

    private var currentFragment: Fragment? = null
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        // 点击首页中间的按钮，跳转到顶部 TabLayout 页面。
//        binding.btnJump.setOnClickListener {
//            val intent = Intent(this, MyTabLayoutActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnJump1.setOnClickListener {
//            val intent = Intent(this, HandlerTestActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnJump2.setOnClickListener {
//            val intent = Intent(this, BroadcastDynamicActivity::class.java)
//            startActivity(intent)
//        }
//        binding.btnJump3.setOnClickListener {
//            val intent = Intent(this, ServiceActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnJump4.setOnClickListener {
//            val intent = Intent(this, ContentProviderActivity::class.java)
//            startActivity(intent)
//        }




        fragments = listOf(HomeFragment(), BookShelfFragment(), CategoryFragment(), PersonFragment())
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Toasty.success(this, "Home首页", Toasty.LENGTH_SHORT).show()
                     showFragment(0)
                    true
                }
                R.id.navigation_book_shelf -> {
                    Toasty.success(this, "Book Shelf书架", Toasty.LENGTH_SHORT).show()
                    showFragment(1)
                    true
                }
                R.id.navigation_category -> {
                    Toasty.success(this, "Category分类", Toasty.LENGTH_SHORT).show()
                    showFragment(2)
                    Log.d("切换Fragment", "切换到了category")
                    true
                }
                R.id.navigation_person -> {
                    Toasty.success(this, "Person个人中心玩", Toasty.LENGTH_SHORT).show()
                    showFragment(3)
                    true
                }
                else -> false
            }
        }
        //初始化时显示第一个Fragment主页
        showFragment(0)
    }

    // FragmentManager：用于Fragment的增删替换操作，它就是Fragment事务管理器
    private fun showFragment1(position: Int) {
        // 1. 获取系统自带的Fragment事务管理器（用于Fragment的增删替换操作）
        supportFragmentManager.beginTransaction()
            // 2. replace操作：替换容器内的Fragment
            // 参数1 fragmentContainerView：承载Fragment的布局容器id
            // 参数2 fragments[position]：预先存好的Fragment数组，根据下标取出要展示的目标Fragment
            .replace(R.id.fragmentContainerView, fragments[position])
            // 3. 提交事务，执行真正的页面替换操作
            .commit()
    }
    //replace方法节省内存，但是会重新加载Fragment，无法保存状态
    //因此引入hide+show

    private fun showFragment(position: Int) {
        // 获取目标Fragment对象
        val targetFragment = fragments[position]
        // 开启Fragment事务，所有增删显隐操作都要依托事务执行
        val transaction = supportFragmentManager.beginTransaction()
        // 判断目标Fragment是否还未添加到容器，未添加则执行add创建
        if (!targetFragment.isAdded) {
            // 将Fragment添加到id为fragmentContainerView的布局容器中
            transaction.add(R.id.fragmentContainerView, targetFragment)
        }
        // 如果存在当前正在显示的Fragment，执行隐藏操作
        currentFragment?.let { oldFragment ->
            transaction.hide(oldFragment)
        }

        // 4. 展示目标Fragment
        transaction.show(targetFragment)

        // 5. 提交事务，执行所有hide/show/add操作（主线程同步执行）
        transaction.commit()

        // 6. 更新全局标记，记录当前正在展示的Fragment
        currentFragment = targetFragment
    }



}
