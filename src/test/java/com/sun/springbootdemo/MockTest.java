package com.sun.springbootdemo;

import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.service.MockInterface;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/25 16:19
 */


@Log4j2
public class MockTest extends BaseJnuit5Test {

    @MockBean
    private MockInterface mockInterface;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private Environment environment;

    @Autowired
    ConfigurableApplicationContext context;


    @Test
    public void mockTest() {
        Mockito.when(mockInterface.mock(Mockito.anyString())).thenReturn("Hello World");
        /* Mockito.when(testService.mock(Mockito.anyString(), Mockito.anyInt())).thenReturn("Hello World");*/
        System.out.println(mockInterface.mock(""));
    }
    

}
