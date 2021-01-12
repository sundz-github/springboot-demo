package com.sun.springbootdemo.retry;

import com.sun.springbootdemo.service.entities.Result;

/**
 * <p> 重试机制 </p>
 *
 * @author Sundz
 * @date 2021/1/11 20:17
 */
public interface RetryService {
    Result<String> apply(String param);
}
