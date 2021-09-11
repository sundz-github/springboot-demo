package com.sun.springbootdemo.thread;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

/**
 * <p> 生产者与消费者 </p>
 *
 * @author Sundz
 * @date 2020/12/24 14:48
 */
@Log4j2
public class ProducerComsumer {


    /**
     * @field A, B, C, D 四个窗口同时卖票
     */
    @Test
    public void threadTest() {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.buy();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.buy();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.buy();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.buy();
            }
        }, "D").start();


    }

    class Ticket {

        int totalNumber = 40;

        public synchronized void buy() {
            log.info("thread->>" + Thread.currentThread().getName() + " ,当前剩余票数:" + totalNumber);
            --totalNumber;
        }

    }
}
