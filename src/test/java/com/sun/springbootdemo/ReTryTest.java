package com.sun.springbootdemo;

import com.sun.springbootdemo.retry.RetryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/1/24 18:11
 */
public class ReTryTest extends BaseJnuit5Test {

    @Autowired
    private RetryService retryService;

    @Test
    @DisplayName("重试机制")
    public void applyTest() {
        retryService.apply("Hello World");
    }
}
