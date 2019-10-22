package com.xingray.sample;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务执行器
 *
 * @author : leixing
 * @date : 2018/5/18
 * Version : 0.0.1
 */
@SuppressWarnings("unused")
public class TaskExecutor {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(4, Math.min(CPU_COUNT - 1, 8));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * IO读写线程池,最多CORE_POOL_SIZE同时执行
     */
    private static volatile ExecutorService sIOPool;

    /**
     * 全局cachePool,适用于AsyncHttpClient等不
     * 限制任务数的请求
     */
    private static volatile ExecutorService sCachePool;

    /**
     * 串行线程池
     */
    private static volatile ExecutorService sSerialPool;

    /**
     * 繁重 任务线程池，适用于像ImageLoader转换图像这种时间不长但又很占CPU的任务
     * 排队执行的{@link ThreadPoolExecutor},核心线程为CORE_POOL_SIZE+1个
     */
    private static volatile ExecutorService sCpuPool;

    /**
     * 主线程{@link Handler}对象，用于将任务提交至主线程执行
     */
    private static volatile Handler uiHandler;

    /**
     * 主线程{@link Executor}对象，通过{@link Handler}实现{@link Executor#execute(Runnable)}方法
     */
    private static volatile Executor sUIExecutor;

    private TaskExecutor() {
        throw new UnsupportedOperationException();
    }

    public static void ui(Runnable task) {
        getHandler().post(task);
    }

    public static void ui(Runnable task, long delayMills) {
        getHandler().postDelayed(task, delayMills);
    }

    public static void io(Runnable task) {
        ioPool().execute(task);
    }

    public static void cpu(Runnable task) {
        cpuPool().execute(task);
    }

    public static void enqueue(Runnable task) {
        serialPool().execute(task);
    }

    public static void infinite(Runnable task) {
        cachePool().execute(task);
    }

    private static Handler getHandler() {
        if (uiHandler == null) {
            synchronized (TaskExecutor.class) {
                if (uiHandler == null) {
                    uiHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return uiHandler;
    }

    public static Executor uiPool() {
        if (sUIExecutor == null) {
            synchronized (TaskExecutor.class) {
                if (sUIExecutor == null) {
                    sUIExecutor = command -> getHandler().post(command);
                }
            }
        }
        return sUIExecutor;
    }

    public static Executor ioPool() {
        if (sIOPool == null) {
            synchronized (TaskExecutor.class) {
                if (sIOPool == null) {
                    sIOPool = new ThreadPoolExecutor(
                            CORE_POOL_SIZE,
                            15,
                            5L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(),
                            new NamedThreadFactory("io-pool"));
                }
            }
        }
        return sIOPool;
    }

    private static Executor cpuPool() {
        if (sCpuPool == null) {
            synchronized (TaskExecutor.class) {
                if (sCpuPool == null) {
                    sCpuPool = new ThreadPoolExecutor(
                            1,
                            1,
                            5L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(128),
                            new NamedThreadFactory("cpu-pool"));
                }
            }
        }
        return sCpuPool;
    }

    private static Executor serialPool() {
        if (sSerialPool == null) {
            synchronized (TaskExecutor.class) {
                if (sSerialPool == null) {
                    sSerialPool = new ThreadPoolExecutor(
                            1,
                            1,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<>(),
                            new NamedThreadFactory("serial-pool"));
                }
            }
        }
        return sSerialPool;
    }

    private static Executor cachePool() {
        if (sCachePool == null) {
            synchronized (TaskExecutor.class) {
                if (sCachePool == null) {
                    sCachePool = new ThreadPoolExecutor(
                            0,
                            MAXIMUM_POOL_SIZE,
                            60L, TimeUnit.SECONDS,
                            new SynchronousQueue<>(),
                            new NamedThreadFactory("cache-pool"));
                }
            }
        }
        return sCachePool;
    }

    private static class NamedThreadFactory implements ThreadFactory {

        private final String mThreadName;
        private final AtomicInteger mCount;

        NamedThreadFactory(String threadName) {
            mThreadName = threadName;
            mCount = new AtomicInteger(1);
        }

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, mThreadName + "#" + mCount.getAndIncrement());
        }
    }
}
