package com.sun.springbootdemo.thread;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> 10个人抢红包 —— 资源类 </p>
 * 1、红包必须抢完；
 * 2、10个人的红包累计金额不能超过总数，也就是10元
 * 3、保证同时开始抢
 *
 * @author Sundz
 * @date 2020/12/25 15:59
 */
@Log4j2
public class QiangHongBao {


    /*public QiangHongBao(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }*/

    /**
     * @field 红包总金额
     */
    private volatile double total = 10;

    private AtomicInteger numberOfPeople = new AtomicInteger(0);

    /**
     * @field 保证主线程不会先执行，必须等10个人抢完所有的红包后才能执行
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    /**
     * @field 数据格式化
     */
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    /**
     * @field 保证10个人同时开始抢，也就是准备阶段，必须同步
     */
    private final CyclicBarrier barrier = new CyclicBarrier(10, () -> System.out.println("所有人都准备好了，可以开始抢红包了 -->> Beginning!!!"));

    private final Lock lock = new ReentrantLock();

    public double handle() throws Exception {
        // 等待  必须等10个都准备好了  才能开始抢红包
        barrier.await();
        lock.lock();
        // 如果红包金额为0了  则说明红包已经被抢完了
        double random = 0;
        // 保留2位小数即可
        total = Double.parseDouble(decimalFormat.format(total));
        long count = countDownLatch.getCount();
        try {
            if (total == 0) {
                countDownLatch.countDown();
                log.info(Thread.currentThread().getName() + " -->> 红包被抢完了，你手速太慢了!");
                log.info("当前没抢红包的人数有:{}", count);
                return 0;
            }
            // 防止红包没有被抢完  强制最后一个人获取红包的剩余金额
            if (numberOfPeople.get() == 9) {
                log.info("最后一个小伙伴( " + Thread.currentThread().getName() + ") -->> 抢到的红包金额为:{}元，红包剩余:{}元", total, 0);
                // 此时红包已被全部抢完了
                total = 0;
                countDownLatch.countDown();
                log.info("当前没抢红包的人数有:{}", count);
                return total;
            }
            random = getRandom();
            /**
             * @field 改变红包金额
             */
            total -= random;
            // 统计抢红包的人数  +1
            numberOfPeople.incrementAndGet();
            log.info(Thread.currentThread().getName() + " -->> 抢到的红包金额为:{}元，红包剩余:{}元", random, total);
            /**
             * @field 每一个人抢完红后需要统计  防止主线程先执行  导致程序结束了
             */
            countDownLatch.countDown();
            log.info("当前没抢红包的人数有:{}", count);
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + "获取锁时出现了异常!");
        } finally {
            // 一定要释放锁  防止发生死锁
            lock.unlock();
        }
        return random;
    }

    /**
     * @field 获取红包随机数
     */
    public double getRandom() {
        Random random = new Random(System.nanoTime());
        return Double.parseDouble(decimalFormat.format(random.nextDouble() * 2));
    }

    @Test
    public void tets() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        QiangHongBao q = new QiangHongBao();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    q.handle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        long count = countDownLatch.getCount();
        countDownLatch.await();
        log.info("红包抢完啦，感谢各位的参与!");
    }

    public static void main(String[] args) throws Exception {
        //CountDownLatch countDownLatch = new CountDownLatch(10);
        QiangHongBao q = new QiangHongBao();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    q.handle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        long count = countDownLatch.getCount();
        countDownLatch.await();
        log.info("红包抢完啦，感谢各位的参与!");
    }


}
