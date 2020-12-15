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
@Async  // 异步调用  防止任务被阻塞
public class TimeTask {

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void excute() throws InterruptedException {
        log.info("定时任务<excute>开始执行了 ==> thread{}:", Thread.currentThread().getName());
        TimeUnit.MILLISECONDS.sleep(10000);
    }

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void excute2() {
        log.info("定时任务开始执行了 ==> thread{}", Thread.currentThread().getName());
    }
}
