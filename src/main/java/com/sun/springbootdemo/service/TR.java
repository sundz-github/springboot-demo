package com.sun.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/25 18:02
 */
@Service
public class TR {

    @Autowired
    private TestService testService;

    public String fun() {
        return testService.mock("sundz", 10);
    }
}
