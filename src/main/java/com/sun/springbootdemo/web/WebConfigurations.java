package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:采
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/18 14:18
 */
@Configuration
@Log4j2
public class WebConfigurations {


    @EventListener(value = DefinitionEvent.class)
    public void envetHandle(DefinitionEvent d) {
        System.out.println("@EventListener注解监听：" + d);

    }

    /**
     * 自定义DispatcherServlet分发器  支持多种路径映射
     *
     * @return {@link ServletRegistrationBean < DispatcherServlet >}
     */
    @Bean
    public ServletRegistrationBean<DefinitionServlet> definitiaonDispatcherServlet() {
        ServletRegistrationBean<DefinitionServlet> servletRegistrationBean = new ServletRegistrationBean<>(new DefinitionServlet());
        servletRegistrationBean.addUrlMappings("/app/*");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setName("definitiaonDispatcherServlet");
        return servletRegistrationBean;
    }

    /**
     * @description: 将过滤器bean注入容器
     * @author: paraview
     */
    @Bean
    public FilterRegistrationBean<? extends Filter> filter() {
        FilterRegistrationBean<CustomizeWebFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CustomizeWebFilter());
        // 针对test请求做特殊处理
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("url", "/test");
        // 添加自定义参数 -->> Map集合  filterRegistrationBean.setInitParameters()
        EnumSet<DispatcherType> dispatcherTypesEnum = EnumSet.allOf(DispatcherType.class);
        dispatcherTypesEnum.add(DispatcherType.REQUEST);
        filterRegistrationBean.setDispatcherTypes(dispatcherTypesEnum);
        Map<String, String> map = new HashMap<>();
        map.put("username", "root");
        map.put("password", "123456");
        filterRegistrationBean.setInitParameters(map);
        return filterRegistrationBean;
    }

   /* @Bean
    public ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new ServletRegistrationBean<>(dispatcherServlet, "/App/*");
    }*/

    /*@Bean
    public ServletListenerRegistrationBean<MyListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<MyListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new MyListener());
        listener.setOrder(1);
        return listener;
    }*/

    /**
     *
     * @param  只有指定的监听器才能被注册，例如：ServletContextAttributeListener，ServletRequestListener
     *  ServletRequestAttributeListener、HttpSessionAttributeListener、HttpSessionListener、
     * @return {@link ServletListenerRegistrationBean<SimpleDefinitionListen>}
     */
    /*@Bean
    public ServletListenerRegistrationBean<SimpleDefinitionListen> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SimpleDefinitionListen> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new SimpleDefinitionListen());
        listener.setOrder(1);
        return listener;
    }*/

}
