package com.sun.springbootdemo.config;

import com.sun.springbootdemo.controller.JerseyController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/9 10:55
 */
@Configuration
@ApplicationPath("/jersey")  // 相当于在配置文件配置spring.jersey.application-path
public class JerseyConfig extends ResourceConfig {

    // 单个注册,packages支持批量注册
    public JerseyConfig() {
        register(JerseyController.class);
        packages("com.sun.springbootdemo.controller");
    }

}
