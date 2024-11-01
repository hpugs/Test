package com.hpugs.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThradPoolTest {

    private static AtomicInteger index = new AtomicInteger(1);

    private static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);

    private static ThreadFactory threadFactory = new ThreadFactory(){

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("线程" + index.getAndIncrement());
            return thread;
        }

    };

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
            5, 10, TimeUnit.SECONDS,
            arrayBlockingQueue,
            threadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 线程池在核心线程数运行时，优先将任务加入队列等待，如果队列已满，线程未到最大线上数，线程池将继续创建线程进行任务执行
        // 观察创建12、13、15、16个线程时，线程的执行名称
        // 12：核心线程2个，队列10个
        // 13：核心线程3个，队列10个
        // 15：核心线程5个，队列10个
        // 16：核心线程5个，队列10个，1个线程走拒绝策略
        for (int i = 0; i < 16; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "执行结束");
                }
            });
        }

        long taskCount = threadPoolExecutor.getTaskCount();
        System.out.println("线程任务数：" + taskCount);

    }

}
