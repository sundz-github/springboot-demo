package com.sun.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/23 17:07
 */
@Service
public class TestService {

    @Autowired
    private CommonBusiniessService commonBusiniessService;


    public void test() {
        for (int i = 0; i < 3; i++) {
            System.out.println(commonBusiniessService.testCache(107));
        }

    }

    public String mock(String name, int id) {
        return id + ":" + name;
    }


}
