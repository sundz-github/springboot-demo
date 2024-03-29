package com.sun.springbootdemo.thread;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> A,B,C,D  -->> 4个字母轮流执行</p>
 *
 * @author Sundz
 * @date 2020/12/28 20:07
 */
@Log4j2
public class RoundRobinExecution {


    /**
     * @field 输出字母数组
     */
    private final String[] alphabetArr = {"A", "B", "C", "D"};

    private final CountDownLatch latch = new CountDownLatch(12);


    /**
     * @field 多线程轮流输出 A, B, C, D
     */
    private class Letter {

        /**
         * @field 可重入锁
         */
        private final Lock lock = new ReentrantLock();

        /**
         * @field A监控器 对应输出A的线程
         */
        private final Condition conditionA = lock.newCondition();
        /**
         * @field B监控器 对应输出B的线程
         */
        private final Condition conditionB = lock.newCondition();
        /**
         * @field C监控器 对应输出C的线程
         */
        private final Condition conditionC = lock.newCondition();
        /**
         * @field D监控器 对应输出D的线程
         */
        private final Condition conditionD = lock.newCondition();

        /**
         * @field 用于标记是否已经执行打印
         */
        /*private volatile boolean status = true;*/
        private AtomicInteger status = new AtomicInteger(0);

        /**
         * 输出字母A
         *
         * @param letter 要输出的字母
         * @return {@link String} 返回值
         */
        public void printA(String letter) {
            lock.lock();
            try {
                // 只要不是字母A则等待
                while (status.get() != 0) {
                    conditionA.await(); // 等待 交出CPU控制权
                }
                // 字母A  则输出，同时需要唤醒线程B（也就是输出B的线程）
                log.info(Thread.currentThread().getName() + "-->输出的字母为:{}", letter);
                conditionB.signal(); // 唤醒B线程
                status.incrementAndGet();
                /*status = true;*/  // 重置状态
                // A线程已完成
                latch.countDown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                lock.unlock();  // 必须在finally中释放锁  否则会引起死锁
            }
        }

        /**
         * 输出字母B
         *
         * @param letter 要输出的字母
         * @return {@link String} 返回值
         */
        public void printB(String letter) {
            lock.lock();
            try {
                // 只要不是字母B则等待
                while (status.get() != 1) {
                    conditionB.await(); // 等待 交出CPU控制权
                }
                // 字母B  则输出，同时需要唤醒线程C（也就是输出C的线程）
                log.info(Thread.currentThread().getName() + "-->输出的字母为:{}", letter);
                conditionC.signal(); // 唤醒C线程
                /* status = false;*/  // 重置状态
                // B线程已完成
                latch.countDown();
                status.incrementAndGet();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                lock.unlock();  // 必须在finally中释放锁  否则会引起死锁
            }
        }

        /**
         * 输出字母B
         *
         * @param letter 要输出的字母
         * @return {@link String} 返回值
         */
        public void printC(String letter) {
            lock.lock();
            try {
                // 只要不是字母C则等待
                while (status.get() != 2) {
                    conditionC.await(); // 等待 交出CPU控制权
                }
                // 字母C  则输出，同时需要唤醒线程D（也就是输出D的线程）
                log.info(Thread.currentThread().getName() + "-->输出的字母为:{}", letter);
                conditionD.signal(); // 唤醒C线程
                /*status = true; */ // 重置状态
                // C线程已完成
                status.incrementAndGet();
                latch.countDown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                lock.unlock();  // 必须在finally中释放锁  否则会引起死锁
            }
        }

        /**
         * 输出字母D
         *
         * @param letter 要输出的字母
         * @return {@link String} 返回值
         */
        public void printD(String letter) {
            lock.lock();
            try {
                // 只要不是字母D则等待
                while (status.get() != 3) {
                    conditionD.await(); // 等待 交出CPU控制权
                }
                // 字母D  则输出，同时需要唤醒线程A（也就是输出D的线程）
                log.info(Thread.currentThread().getName() + "-->输出的字母为:{}", letter);
                conditionA.signal(); // 唤醒A线程
                /*status = false; */ // 重置状态
                // D线程已完成
                status.set(0);
                latch.countDown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                lock.unlock();  // 必须在finally中释放锁  否则会引起死锁
            }
        }
    }

    @Test
    public void threadTest() throws Exception {
        Letter letter = new Letter();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                letter.printB(alphabetArr[1]);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                letter.printD(alphabetArr[3]);
            }
        }, "D").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                letter.printC(alphabetArr[2]);
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                letter.printA(alphabetArr[0]);
            }
        }, "A").start();

        //long count = latch.getCount();
        //防止先主线程执行完
        latch.await();
        //TimeUnit.SECONDS.sleep(20);
    }

}
