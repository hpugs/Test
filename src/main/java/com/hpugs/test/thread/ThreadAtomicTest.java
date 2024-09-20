package com.hpugs.test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadAtomicTest {

    private static Integer num = 0;

    private static AtomicInteger atomicNum = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        test1();

        test2();
    }

    public static void test1() throws InterruptedException {
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        num++;
                    }
                }
            });
            list.add(thread);
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println("num=" + num);
    }

    public static void test2() throws InterruptedException {
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        atomicNum.getAndIncrement();
                    }
                }
            });
            list.add(thread);
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println("atomicNum=" + atomicNum.get());
    }

}
