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

    @Bean
    public ExecutorService executorService() {

        //core-size:核心线程数  一般为CPU核数的2倍
        //queue：线程队列
        //Max-size：最大线程数
        //thread-factory: 线程工厂
        //refuse-plocy;拒绝策略
        //keep-alive：线程空闲时间
        return new ThreadPoolExecutor(16, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
