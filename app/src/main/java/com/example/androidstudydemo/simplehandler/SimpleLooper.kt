package com.example.androidstudydemo.simplehandler

/**
 * 简易Looper，类似 android.os.Looper
 *
 * 一个线程只能创建一个Looper。
 */
class SimpleLooper private constructor() {

    internal val messageQueue = SimpleMessageQueue()

    /**
     * 退出消息循环
     */
    fun quit() {
        messageQueue.quit()
    }

    companion object {

        private val threadLocal = ThreadLocal<SimpleLooper>()

        /**
         * 为当前线程创建Looper
         */
        fun prepare() {
            check(threadLocal.get() == null) {
                "当前线程已经创建过SimpleLooper"
            }

            threadLocal.set(SimpleLooper())
        }

        /**
         * 获取当前线程的Looper
         */
        fun myLooper(): SimpleLooper? {
            return threadLocal.get()
        }

        /**
         * 开启消息循环
         */
        fun loop() {
            val looper = myLooper()
                ?: throw IllegalStateException(
                    "当前线程没有SimpleLooper，请先调用SimpleLooper.prepare()"
                )

            while (true) {
                val message = looper.messageQueue.next() ?: break

                try {
                    message.target?.dispatchMessage(message)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }
}