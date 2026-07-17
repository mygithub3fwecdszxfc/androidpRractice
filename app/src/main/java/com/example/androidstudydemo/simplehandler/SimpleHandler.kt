package com.example.androidstudydemo.simplehandler

/**
 * 简易Handler，类似 android.os.Handler
 */
open class SimpleHandler(
    private val looper: SimpleLooper = SimpleLooper.myLooper()
        ?: throw IllegalStateException(
            "创建SimpleHandler前必须先调用SimpleLooper.prepare()"
        )
) {

    private val messageQueue = looper.messageQueue

    /**
     * 立即发送消息
     */
    fun sendMessage(message: SimpleMessage): Boolean {
        return sendMessageDelayed(message, 0L)
    }

    /**
     * 延迟发送消息
     */
    fun sendMessageDelayed(
        message: SimpleMessage,
        delayMillis: Long
    ): Boolean {
        message.target = this
        return messageQueue.enqueueMessage(message, delayMillis)
    }

    /**
     * 简化发送方式
     */
    fun sendEmptyMessage(what: Int): Boolean {
        return sendMessage(SimpleMessage(what))
    }

    /**
     * 延迟发送空消息
     */
    fun sendEmptyMessageDelayed(
        what: Int,
        delayMillis: Long
    ): Boolean {
        return sendMessageDelayed(
            message = SimpleMessage(what),
            delayMillis = delayMillis
        )
    }

    /**
     * 分发消息
     */
    internal fun dispatchMessage(message: SimpleMessage) {
        handleMessage(message)
    }

    /**
     * 子类重写此方法处理消息
     */
    open fun handleMessage(message: SimpleMessage) {
        // 默认不处理
    }

    /**
     * 移除指定消息
     */
    fun removeMessages(what: Int) {
        messageQueue.removeMessages(what)
    }

    /**
     * 清空所有消息
     */
    fun removeAllMessages() {
        messageQueue.clear()
    }
}