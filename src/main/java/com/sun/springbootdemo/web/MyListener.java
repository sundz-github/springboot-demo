package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/21 15:56
 */
@WebListener
@Log4j2
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("<----------------容器初始化---------------->");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("<----------------容器销毁---------------->");
    }
}
