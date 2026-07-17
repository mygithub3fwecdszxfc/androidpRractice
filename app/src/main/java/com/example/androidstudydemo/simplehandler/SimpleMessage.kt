package com.example.androidstudydemo.simplehandler

/**
 * 简易消息对象，类似 android.os.Message
 */
data class SimpleMessage(
    val what: Int,
    val obj: Any? = null,
    internal var executeTime: Long = 0L,
    internal var sequence: Long = 0L,
    internal var target: SimpleHandler? = null
)