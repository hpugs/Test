package com.hpugs.test.threadlocal;

import com.hpugs.test.dto.UserDTO;

public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<UserDTO> threadLocal = new ThreadLocal();
        UserDTO userDTO = new UserDTO(1L, "张三");
        threadLocal.set(userDTO);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDTO userDTO = threadLocal.get();
                System.out.println("thread1 outPut:" + userDTO);

                userDTO = new UserDTO(2L, "王五");
                threadLocal.set(userDTO);
            }
        });

        thread1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        userDTO = threadLocal.get();
        System.out.println("main thread outPut:" + userDTO);

        // 移除threadLocal
        threadLocal.remove();
    }

}
