package com.will.Test;

import com.will.factory.ThreadPoolExecutorFactory;

/**
 * ClassName:DemoTest
 * Description:测试以及对比
 *
 * @Author Will Wu
 * @Email willwu618@gmail.com
 * @Date 2017-12-26
 */
public class DemoTest {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        for ( int i = 0; i < 100; i++) {
            new Thread(() -> {
            }).start();
        }
        System.out.println(System.currentTimeMillis());

        for ( int i = 0; i < 100; i++) {
            ThreadPoolExecutorFactory.getThreadPoolExecutor().execute(() -> {
            });
        }
        System.out.println(System.currentTimeMillis());
    }
}
