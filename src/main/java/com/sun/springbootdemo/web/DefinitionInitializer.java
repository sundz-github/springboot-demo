package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description:容器刷新之前调用该类的initialize方法,并将ConfigurableApplicationContext类的实例传递给该方法
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/23 20:51
 */
@Log4j2
public class DefinitionInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private ConfigurableApplicationContext context;

    /**
     * @field 用于初始化上下文
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("<-------------------自定义初始化容器起作用了------------------->");
        context = applicationContext;
    }
}
