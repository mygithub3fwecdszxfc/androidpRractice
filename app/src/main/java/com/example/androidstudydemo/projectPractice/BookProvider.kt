package com.example.androidstudydemo.projectPractice

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

/**
 * 自定义内容提供者，对外提供书籍数据，底层Binder实现跨进程数据共享
 */
class BookProvider : ContentProvider() {

    /**
     * companion object 伴生对象：Kotlin替代Java static，存放全局静态常量，无需创建对象即可调用
     */
    companion object {
        // AUTHORITY：Provider全局唯一标识，整台手机所有App不能重复，和Manifest注册值保持一致
        const val AUTHORITY = "com.example.androidstudydemo.projectPractice.bookprovider"
        // 匹配码1：匹配地址 content://xxx/books 代表查询全部书籍
        const val BOOKS = 1
        // 匹配码2：匹配地址 content://xxx/books/数字 代表查询单本指定ID书籍
        const val BOOK_SINGLE = 2

        // 对外统一访问根Uri，客户端所有操作都基于这个地址
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/books")

        // Uri匹配工具：用来判断当前请求是查全部还是单条数据
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            // 参数1：authority，参数2：路径，参数3：匹配标识
            addURI(AUTHORITY, "books", BOOKS)
            addURI(AUTHORITY, "books/#", BOOK_SINGLE)
            // # 通配符，匹配任意数字（书籍id）
        }
    }

    // 模拟数据库：可变集合存放书籍数据，代替SQLite数据库简化练习
    private val mockBooks = mutableListOf <Map<String, Any>> (
        mapOf("_id" to 1, "title" to "斗破苍穹", "author" to "天蚕土豆", "coverUrl" to "https://example.com/1.jpg"),
        mapOf("_id" to 2, "title" to "斗罗大陆", "author" to "唐家三少", "coverUrl" to "https://example.com/2.jpg"),
        mapOf("_id" to 3, "title" to "完美世界", "author" to "辰东", "coverUrl" to "https://example.com/3.jpg"),
    )

    /**
     * 查询数据 读操作
     * @param uri 请求的数据地址
     * @param projection 需要查询的字段数组
     * @param selection 查询筛选条件where
     * @param selectionArgs 条件占位符填充值
     * @param sortOrder 结果排序规则
     * @return 封装数据的Cursor，跨进程标准容器
     */
    override fun query(
        uri: Uri, projection: Array<out String>?,
        selection: String?, selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // 定义游标返回的所有字段（表头）
        val columns = arrayOf("_id", "title", "author", "coverUrl")
        // MatrixCursor 内存游标，把List数据封装成系统支持跨进程传输的Cursor
        val cursor = MatrixCursor(columns)

        // 匹配当前Uri的类型
        when (uriMatcher.match(uri)) {
            // 查询全部书籍
            BOOKS -> {
                for (book in mockBooks) {
                    cursor.addRow(arrayOf(book["_id"], book["title"], book["author"], book["coverUrl"]))
                }
            }
            // 查询单本，截取uri末尾的id匹配数据
            BOOK_SINGLE -> {
                val id = uri.lastPathSegment?.toIntOrNull()
                val book = mockBooks.find { it["_id"] == id }
                if (book != null) {
                    cursor.addRow(arrayOf(book["_id"], book["title"], book["author"], book["coverUrl"]))
                }
            }
        }
        return cursor
    }

    /**
     * 插入数据 增操作
     * @param uri 插入数据的根地址
     * @param values 客户端传递的待插入字段键值对
     * @return 新创建书籍的完整Uri
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 自增主键id
        val id = mockBooks.size + 1
        // 组装新书籍map，空值兜底
        val newBook = mapOf(
            "_id" to id,
            "title" to (values?.getAsString("title") ?: ""),
            "author" to (values?.getAsString("author") ?: ""),
            "coverUrl" to (values?.getAsString("coverUrl") ?: "")
        )
        mockBooks.add(newBook)
        // 通知所有监听此Uri的页面，数据已变更，自动刷新
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        // 拼接返回单条书籍的Uri
        return Uri.withAppendedPath(CONTENT_URI, id.toString())
    }

    /**
     * 删除数据 删操作
     * @return 删除成功的行数
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            // 删除单条指定id书籍
            BOOK_SINGLE -> {
                val id = uri.lastPathSegment?.toIntOrNull()
                val removed = mockBooks.removeAll { it["_id"] == id }
                if (removed) 1 else 0
            }
            // 清空全部书籍
            BOOKS -> {
                val size = mockBooks.size
                mockBooks.clear()
                size
            }
            else -> 0
        }
    }

    /**
     * 更新数据 修改操作
     * @return 修改成功的行数
     */
    override fun update(
        uri: Uri, values: ContentValues?,
        selection: String?, selectionArgs: Array<out String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            BOOK_SINGLE -> {
                val id = uri.lastPathSegment?.toIntOrNull()
                val book = mockBooks.find { it["_id"] == id }
                if (book != null) {
                    val index = mockBooks.indexOf(book)
                    val updated = book.toMutableMap()
                    // 覆盖传入的新字段
                    values?.getAsString("title")?.let { updated["title"] = it }
                    values?.getAsString("author")?.let { updated["author"] = it }
                    values?.getAsString("coverUrl")?.let { updated["coverUrl"] = it }
                    mockBooks[index] = updated
                    1
                } else 0
            }
            else -> 0
        }
    }

    /**
     * 获取当前Uri对应数据的MIME类型，：给系统返回当前这个Uri对应的数据是什么类型，分两大类：多条集合 / 单条对象。
     */
    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            // dir 代表多条数据集合
            BOOKS -> "vnd.android.cursor.dir/books"
            // item 代表单条数据
            BOOK_SINGLE -> "vnd.android.cursor.item/books"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    // Provider创建时初始化，返回true代表初始化成功
    override fun onCreate(): Boolean = true
}