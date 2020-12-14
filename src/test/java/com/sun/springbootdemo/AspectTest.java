package com.sun.springbootdemo;

import com.sun.springbootdemo.config.CommonApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p> 切面测试 </p>
 *
 * @author Sundz
 * @date 2020/12/12 12:43
 */
public class AspectTest extends BaseJnuit5Test {

    @Autowired
    private CommonApi commconProperty;

    @Test
    public void afterReturningTest() {
        String result = commconProperty.test2("Hello World");
    }


}
