package com.sun.springbootdemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/9 10:53
 */
@Log4j2
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class JerseyApplication {
    public static void main(String[] args) {
        SpringApplication.run(JerseyApplication.class, args);
        log.info("<---------------JerseyApplication工程正在启动--------------->");
    }
}
