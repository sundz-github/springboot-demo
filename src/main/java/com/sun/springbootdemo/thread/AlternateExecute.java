package com.sun.springbootdemo.thread;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p> 多个线程交替执行 == >> 线程间需要通信 </p>
 *
 * @author Sundz
 * @date 2020/12/24 18:00
 */
@Log4j2
public class AlternateExecute {


    @Test
    @SneakyThrows
    public void threadTest() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        Semaphore semaphore = new Semaphore(2);
        Reset reset = new Reset();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                reset.decrease();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                reset.increase();
            }
        }, "A").start();
        TimeUnit.SECONDS.sleep(40);
    }

    class Reset {

        /**
         * @field 复位状态
         */
        private int status = 0;


        public synchronized void increase() {
            try {
                // 阻塞  等待  防止多次累加
                while (status != 0) {
                    this.wait();  // 让出CPU的控制权  要用while防止虚假等待
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            status++;
            log.info(Thread.currentThread().getName() + ", status:" + status);
            // 需要通知其他线程  可以进行减操作了
            this.notifyAll();
        }

        public synchronized void decrease() {
            try {
                // 当status为0时  需要等待
                while (status == 0) {
                    this.wait(); // 让出CPU的控制权  要用while防止虚假等待
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            status--;
            log.info(Thread.currentThread().getName() + ", status:" + status);
            // 需要通知其他的线程  可以进行加操作了
            this.notifyAll();
        }

    }
}
