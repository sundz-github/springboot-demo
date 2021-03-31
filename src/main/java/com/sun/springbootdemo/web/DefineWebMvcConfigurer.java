package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> 全局配置注入拦截器 </p>
 *
 * @author Sundz
 * @date 2021/2/7 10:11
 */
@Configuration
@Log4j2
public class DefineWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截器加载了!");
        registry.addInterceptor(new DefinitionInterceptor());
    }
}
