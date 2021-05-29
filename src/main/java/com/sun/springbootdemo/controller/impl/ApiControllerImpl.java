package com.sun.springbootdemo.controller.impl;

import com.sun.springbootdemo.controller.api.ApiController;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sundz
 * @since 2020/10/20 11:29
 */
@RestController
public class ApiControllerImpl implements ApiController {

    @Override
    public String b(String param) {
        return param;
    }


    @Override
    public String a(String param) {
        return param;
    }

    @GetMapping(value = "/pair")
    public Pair<String, Object> pair(String param) {
        return new Pair<>("Hi", param);


    }
}
