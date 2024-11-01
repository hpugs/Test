package com.hpugs.test.thread;

public class ThreadJoinTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B");
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("C");
            }
        });

        thread1.join();
        thread2.join();
        thread3.join();

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
