package com.sun.springbootdemo.retry;

import com.sun.springbootdemo.service.entities.Result;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * <p> spring-retry重试机制 </p>
 *
 * @author Sundz
 * @date 2021/1/11 20:38
 */
@Service
public class RetryServiceImpl implements RetryService {

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 3000, maxDelay = 10000, multiplier = 1))
    @Override
    public Result<String> apply(String param) {
        return null;
    }
}
