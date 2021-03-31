package com.sun.springbootdemo.schedul;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p> 定时任务 </p>
 * 注意多个定时任务且单线程时，如果前面有任务被阻塞，则会影响后面任务的执行
 *
 * @author Sundz
 * @date 2020/12/15 16:04
 */
@Log4j2
@Component  // 需要交给spring ioc容器管理
@Async(value = "executorService")  // 需要自定义线程池  否则就是单一线程(Executors.newSingleThreadExecutor())
public class TimerTask {


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void excute() throws InterruptedException {
        log.info("定时任务<do1>开始执行了 ==> thread{}:", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(6);
    }

    @Scheduled(cron = "0 0/2 * * * ? ")
    public void excute2() {
        log.info("定时任务<do2>开始执行了 ==> thread{}:", Thread.currentThread().getName());
    }
}
