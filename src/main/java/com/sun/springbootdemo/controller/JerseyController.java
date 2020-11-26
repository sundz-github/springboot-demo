package com.sun.springbootdemo.controller;

import com.sun.springbootdemo.controller.api.JerseyControllerApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import java.util.Set;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/8 17:46
 */
@Log4j2
@Component
@Path("/v1")  // 为啥这个必须呢？
public class JerseyController implements JerseyControllerApi {

    @Override
    public String test(String id) {
        log.info("JerseyController控制器生效了!");
        return id;
    }

    @Override
    public String test1() {
        return "Hello World!";
    }


    @Override
    public Set<String> collect(Set<String> set) {
        log.info("collect:{}", set);
        return set;
    }
}

