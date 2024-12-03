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

    /**
     * 当一个任务通过execute(Runnable)方法欲添加到线程池时：
     * <p>
     * 1.如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
     * <p>
     * 2.如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
     * <p>
     * 3.如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，
     * 建新的线程来处理被添加的任务。
     * <p>
     * 4.如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过
     * handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程
     * maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     * <p>
     * 5.当线程池中的线程数量大于corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。
     * 这样，线程池可以动态的调整池中的线程数。
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
            5, 10, TimeUnit.SECONDS,
            arrayBlockingQueue,
            threadFactory,
            // rejection-policy：当pool已经达到max size的时候，如何处理新任务
            // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
            new ThreadPoolExecutor.CallerRunsPolicy());

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
