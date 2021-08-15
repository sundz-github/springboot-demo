package com.sun.springbootdemo;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/8/7 21:54
 */
@Log4j2
public class AsyncTest extends BaseJnuit5Test {

    @Test
    @SneakyThrows
    public void futureTest() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("异步线程:" + Thread.currentThread().getName());
            return "This is callable!";
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        thread.join();
        if (futureTask.isDone()) {
            String result = futureTask.get();
            System.out.println("异步线程返回结果:" + result);
        }
        System.out.println("主线程:" + Thread.currentThread().getName());

    }

    @Test
    @SneakyThrows
    public void executorTest() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService threadPool = null;
        try {
            threadPool = Executors.newFixedThreadPool(3);
            FutureTask<String> futureTask = new FutureTask<>(() -> {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("异步线程:" + Thread.currentThread().getName());
                countDownLatch.countDown();
                return "This is callable!";
            });
            Future<?> future = threadPool.submit(futureTask);
            Object s = futureTask.get();
            System.out.println("异步线程执行结果:" + s);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (!threadPool.isShutdown()) {
                threadPool.shutdown();
            }
        }
        countDownLatch.await();
        System.out.println("主线程:" + Thread.currentThread().getName());
    }


}

