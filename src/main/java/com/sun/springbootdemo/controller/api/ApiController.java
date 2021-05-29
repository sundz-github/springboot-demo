package com.sun.springbootdemo.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sundz
 * @since 2020/10/20 11:26
 */
@RequestMapping("/api")
@Api(tags = "ApiController")
public interface ApiController {

    @GetMapping(value = "b")
    @ApiResponse(code = 200, message = "请求成功", response = String.class)
    String b(@RequestParam("param") String param);


    Pair<String, Object> pair(@RequestParam("param") String param);

    @RequestMapping("a")
    @ApiResponse(code = 200, message = "请求成功"/*, response = String.class*/)
    String a(@RequestParam("param") String param);
}
