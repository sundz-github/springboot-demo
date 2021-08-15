package com.sun.springbootdemo.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p> 自定义线程池 </p>
 *
 * @author Sundz
 * @date 2020/12/25 15:11
 */
@Configuration
public class ThreadPoolConfig {

    private Integer cpuCount = Runtime.getRuntime().availableProcessors();

    @Bean
    public ExecutorService executorService() {

        /**
         * @field (线程空闲时间 / 线程占用时间 + 1)* CPU核数
         */
        //core-size:核心线程数  一般为CPU核数的2倍
        //queue：线程队列
        //Max-size：最大线程数c
        //thread-factory: 线程工厂
        //refuse-plocy;拒绝策略
        //keep-alive：线程空闲时间
        return new ThreadPoolExecutor(cpuCount * 2, cpuCount * 4, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
