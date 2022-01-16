package com.sun.springbootdemo;

import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import com.sundz.service.AnimalService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
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


    @Resource
    private AnimalService animalService;


    @Test
    public void reflectTest() throws Exception {

        AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(SpringbootdemoApplication.class.getAnnotation(MapperScan.class), false, false);
        String value = annotationAttributes.getString("value");

    }

    @Test
    public void proxyTest() {

    }


}
