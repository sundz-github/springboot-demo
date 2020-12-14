package com.sun.springbootdemo;

import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.config.CommonServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
public class BeansTest extends BaseJnuit5Test {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommonServer commonServer;

    @Autowired
    private CommconProperty commonApi;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    public void test1() {
        CommonServer bean = context.getBean(CommonServer.class);
        RestTemplate build = restTemplateBuilder.build();

    }
}
