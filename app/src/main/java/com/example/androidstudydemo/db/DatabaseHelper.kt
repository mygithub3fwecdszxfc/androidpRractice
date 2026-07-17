package com.example.androidstudydemo.db


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import es.dmoral.toasty.Toasty

//class DatabaseHelper(
//    context: Context?,
//    name: String?,
//    factory: SQLiteDatabase.CursorFactory?,
//    version: Int
//) : SQLiteOpenHelper(context, name, factory, version) {
//    override fun onCreate(db: SQLiteDatabase?) {
//    }
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//    }
//}


class DatabaseHelper ( val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1

        // 用户表和表的字段
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        // 创建用户表的SQL语句
        private val SQL_CREATE_USERS = """
        CREATE TABLE $TABLE_USERS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_USERNAME TEXT UNIQUE NOT NULL,
            $COLUMN_PASSWORD TEXT NOT NULL
        )
    """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USERS)
    }

    //    //简单粗暴，先删后建
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//      db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
//        onCreate(db)
//    }
//    或者新旧版本
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //升级策略,给表增加应该字段:年龄
        if (oldVersion < newVersion) {
            db?.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN age INTEGER")
        }
    }

    // 注册用户，插入账号密码到users表，返回注册是否成功
    fun registerUser(username: String, password: String): Boolean {
        // 获取可读写数据库对象，用于执行新增操作
        val db = writableDatabase
        // 封装要插入的字段键值对
        val values = ContentValues().apply {
            // 存入用户名字段
            put(COLUMN_USERNAME, username)
            // 存入密码字段
            put(COLUMN_PASSWORD, password)
        }
        // 执行插入语句，返回新插入数据的行ID；插入失败返回-1
        val result = db.insert(TABLE_USERS, null, values)
        // 判断：行ID不等于-1代表插入成功，返回true；失败返回false
        return result != -1L
    }

    // 判断用户名是否已经存在，存在返回true，不存在返回false
    fun findUsername(username: String): Boolean {
        // 获取只读数据库，查询操作统一用readableDatabase
        val db = readableDatabase
        // query参数说明：表名、查询字段数组、where条件、条件占位符参数、分组、过滤分组、排序
        val cursor = db.query(
            TABLE_USERS,                // 目标表名
            arrayOf(COLUMN_ID),         // 只查询id，减少性能开销
            "$COLUMN_USERNAME = ?",     // where条件：用户名匹配占位符
            arrayOf(username),          // 占位符对应的值，防止SQL注入
            null,                       // groupBy 分组，不需要传null
            null,                       // having 分组过滤，不需要传null
            null                        // orderBy 排序，不需要传null
        )
        // cursor.moveToFirst()：有匹配数据返回true，无数据返回false
        val isExist = cursor.moveToFirst()
        // 游标必须关闭，防止内存泄漏
        cursor.close()
        return isExist
    }

    // 根据用户名删除用户，返回布尔值代表是否删除到数据
    fun deleteUser(username: String): Boolean {
        // 获取可读写数据库，删除属于修改操作，使用writableDatabase
        val db = writableDatabase

        /**
         * delete 参数说明：
         * 1. 目标数据表名
         * 2. where删除条件，?为占位符防止SQL注入
         * 3. 占位符对应参数数组
         * 返回值：受影响的行数（删除了几条数据）
         */
        val deleteRows = db.delete(
            TABLE_USERS,
            "$COLUMN_USERNAME = ?",
            arrayOf(username)
        )
        // 删除行数>0代表存在该用户并删除成功，返回true；无匹配用户返回false
        return deleteRows > 0
    }

    // 根据用户名修改对应用户的密码，返回是否修改成功
    fun updateUser(username: String, newPassword: String): Boolean {
        // 增删改操作使用可读写数据库
        val db = writableDatabase

        // 封装需要更新的字段：只更新password字段
        val updateValues = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }

        /**
         * update 参数说明：
         * 1. 表名
         * 2. 要更新的字段键值对
         * 3. where条件，匹配用户名
         * 4. 条件占位符参数，防止SQL注入
         * 返回值：受影响的数据行数
         */
        val updateCount = db.update(
            TABLE_USERS,
            updateValues,
            "$COLUMN_USERNAME = ?",
            arrayOf(username)
        )

        // 行数大于0：找到用户并完成修改；等于0：无该用户，修改失败
        return updateCount > 0
    }

    //查询找到所有的用户
    @SuppressLint("Range")
    fun queryAllUsers(): List<User> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD),
            null,
            arrayOf(),
            null,
            null,
            null
        )
        // 定义可变列表，用来存放查询到的所有用户
        val userList = mutableListOf<User>()
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                val user = User(id, username, password)
                userList.add(user)
            }
        } finally {
            // 释放游标，防止内存泄漏
            cursor.close()
        }
        return userList
    }

    // 登录校验，返回 0无用户 / 1成功 / 2密码错误
    @SuppressLint("Range")
    fun login(username: String, password: String): Int {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_PASSWORD),
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null, null, null
        )
        return try {
            if (!cursor.moveToFirst()) {
                0
            } else {
                val dbPwd = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                if (dbPwd == password) 1 else 2
            }
        } finally {
            cursor.close()
        }
    }
}