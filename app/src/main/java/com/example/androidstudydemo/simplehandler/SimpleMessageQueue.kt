package com.example.androidstudydemo.simplehandler

import java.util.PriorityQueue
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * 简易消息队列，类似 android.os.MessageQueue
 *
 * PriorityQueue会根据消息执行时间自动排序。
 */
class SimpleMessageQueue {

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private var isQuit = false
    private var sequenceGenerator = 0L

    private val messageQueue = PriorityQueue<SimpleMessage>(
        compareBy<SimpleMessage> { it.executeTime }
            .thenBy { it.sequence }
    )

    /**
     * 将消息加入队列
     */
    fun enqueueMessage(message: SimpleMessage, delayMillis: Long): Boolean {
        lock.withLock {
            if (isQuit) {
                return false
            }

            message.executeTime =
                System.currentTimeMillis() + delayMillis.coerceAtLeast(0L)

            message.sequence = sequenceGenerator++

            messageQueue.offer(message)

            // 唤醒正在等待消息的线程
            condition.signalAll()

            return true
        }
    }

    /**
     * 获取下一条需要执行的消息。
     *
     * 没有消息时阻塞等待；
     * 消息执行时间未到时，等待指定时间。
     */
    fun next(): SimpleMessage? {
        lock.withLock {
            while (true) {
                if (isQuit) {
                    return null
                }

                val message = messageQueue.peek()

                if (message == null) {
                    // 队列为空，无限等待新消息
                    condition.await()
                    continue
                }

                val currentTime = System.currentTimeMillis()
                val waitTime = message.executeTime - currentTime

                if (waitTime <= 0L) {
                    // 消息执行时间已到
                    return messageQueue.poll()
                }

                // 消息还未到执行时间，等待相应时长
                condition.awaitNanos(waitTime * 1_000_000L)
            }
        }
    }

    /**
     * 根据what移除消息
     */
    fun removeMessages(what: Int) {
        lock.withLock {
            messageQueue.removeAll { it.what == what }
            condition.signalAll()
        }
    }

    /**
     * 清空全部消息
     */
    fun clear() {
        lock.withLock {
            messageQueue.clear()
            condition.signalAll()
        }
    }

    /**
     * 退出消息队列
     */
    fun quit() {
        lock.withLock {
            isQuit = true
            messageQueue.clear()
            condition.signalAll()
        }
    }
}