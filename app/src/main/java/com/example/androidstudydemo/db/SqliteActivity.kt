package com.example.androidstudydemo.db

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import es.dmoral.toasty.Toasty

class SqliteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sqlite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DatabaseHelper(this)
    }

    fun registerUser(view: View) {
        val username = "test5"
        val password = "test5"
        val result = dbHelper.registerUser(username, password)
        if (result) {
            // 注册成功
            Toasty.success(this, "注册成功", Toasty.LENGTH_SHORT).show()
        } else {
            // 注册失败
            Toasty.error(this, "注册失败", Toasty.LENGTH_SHORT).show()
        }
    }

    fun findUser(view: View) {
        val username = "test"
        val result = dbHelper.findUsername(username)
        if (result) {
            // 查询成功
            Toasty.success(this, "查询成功", Toasty.LENGTH_SHORT).show()
        } else {
            // 查询失败
            Toasty.error(this, "查询失败", Toasty.LENGTH_SHORT).show()
        }
    }

    fun updateUser(view: View) {
        val username = "test"
        val newPassword = "test123"
        val result = dbHelper.updateUser(username, newPassword)
        if (result) {
            // 修改成功
            Toasty.success(this, "修改成功", Toasty.LENGTH_SHORT).show()
        } else {
            // 修改失败
            Toasty.error(this, "修改失败", Toasty.LENGTH_SHORT).show()
        }
    }

    fun deleteUser(view: View) {
        val username = "test"
        val result = dbHelper.deleteUser(username)
        if (result) {
            // 删除成功
            Toasty.success(this, "删除成功", Toasty.LENGTH_SHORT).show()
        } else {
            // 删除失败
            Toasty.error(this, "删除失败", Toasty.LENGTH_SHORT).show()
        }
    }

    //查询所有用户
    fun findAllUser(view: View): List<User> {
        Toasty.success(this, "查询成功,${dbHelper.queryAllUsers()}", Toasty.LENGTH_SHORT).show()
        println(dbHelper.queryAllUsers())
        return dbHelper.queryAllUsers()

    }

    fun loginUser(view: View) {
        val username = "test7788"
        val password = "test"
        dbHelper.login(username, password)

        println(dbHelper.login(username, password))

    }

}