package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.MarkAnnotation;
import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
@MarkAnnotation(value = "hello", keys = {"1", "2"})
@Log4j2
public class BeansTest extends BaseJnuit5Test {

    private int a;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Autowired
    private XiaoMingImpl xiaoMing;


    @Test
    public void reflectTest() throws Exception {
        Class<BeansTest> clazz = BeansTest.class;
        MarkAnnotation markAnnotation = clazz.getAnnotation(MarkAnnotation.class);
        System.out.println(markAnnotation.value());

    }

    @Test
    public void proxyTest() {
        List<Integer> value = Arrays.asList(1, 2, 0, 7, 8);
        value.forEach(it -> System.out.println(10 / it));
    }
}
