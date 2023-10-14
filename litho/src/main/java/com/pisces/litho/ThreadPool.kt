package com.pisces.litho

import android.os.Process
import com.facebook.rendercore.RunnableHandler
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.math.max

internal object ThreadPool {

    private val count = AtomicInteger(0)

    private val factory = ThreadFactory {
        thread(name = "LayoutThread\$${count.getAndIncrement()}", start = false) {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            it.run()
        }
    }

    internal val lithoHandler = object : RunnableHandler {
        override fun post(runnable: Runnable, tag: String?) {
            threadPool.execute(runnable)
        }

        override fun postAtFront(runnable: Runnable, tag: String?) {
            throw UnsupportedOperationException()
        }

        override fun isTracing(): Boolean = false

        override fun remove(runnable: Runnable) {
            threadPool.remove(runnable)
        }
    }

    private val threadPool: ThreadPoolExecutor = kotlin.run {
        val nThreads = max(
            Runtime.getRuntime().availableProcessors(),
            4
        )
        ThreadPoolExecutor(
            nThreads, nThreads,
            3,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            factory
        )
    }

    val executor: Executor
        get() = threadPool

}