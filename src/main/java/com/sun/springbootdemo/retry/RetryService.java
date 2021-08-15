package com.sun.springbootdemo.retry;

import org.springframework.retry.annotation.Recover;

/**
 * <p> 重试机制 </p>
 *
 * @author Sundz
 * @date 2021/1/11 20:17
 */
public interface RetryService {

    String apply(String param);

    @Recover
    String recover(Exception e, String param);
}
