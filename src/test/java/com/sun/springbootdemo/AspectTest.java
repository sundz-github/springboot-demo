package com.sun.springbootdemo;

import com.sun.springbootdemo.config.CommconProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * <p> 切面测试 </p>
 *
 * @author Sundz
 * @date 2020/12/12 12:43
 */
public class AspectTest extends BaseJnuit5Test {

    @Autowired
    private CommconProperty commconProperty;

    @Test
    public void afterReturningTest() {
        String result = commconProperty.test2("Hello World");
    }

    @Test
    public void uuidTest() {
        System.out.println(UUID.randomUUID().toString());
    }


}
