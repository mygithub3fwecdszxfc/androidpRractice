package com.example.androidstudydemo

import android.content.ContentValues
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidstudydemo.db.DatabaseHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.androidstudydemo", appContext.packageName)
    }

    //测试DatabaseHelper里面的用户数据插入功能
    @Test
    fun insertData_isCorrect() {
        val dbHelper = DatabaseHelper(
            InstrumentationRegistry.getInstrumentation().targetContext,
        )
       val result =  dbHelper.registerUser("张三", "123456")
        assertTrue(result)
    }
    //测试DatabaseHelper里面的用户名查询功能
    @Test
    fun findUsername_isCorrect() {
        val dbHelper = DatabaseHelper(
            InstrumentationRegistry.getInstrumentation().targetContext,
        )
        val result = dbHelper.findUsername("张三")
        assertTrue(result)
    }
}