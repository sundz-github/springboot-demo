package com.sun.springbootdemo.controller;

import com.sun.springbootdemo.service.BaseService;
import com.sun.springbootdemo.service.entities.Person;
import com.sun.springbootdemo.service.entities.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Set;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/3 13:57
 */
@RestController
@RequestMapping(value = "/v1/test")
@Api(value = "TestController控制器", tags = "TestController")
@Log4j2
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  // 隔离数据，防止不同请求出现数据共享
public class TestController {

    private Integer number = 0;

    @Autowired
    private BaseService baseService;

    @GetMapping(value = "/{id}")
    @ApiResponse(code = 200, message = "成功")
    public Result<String> getInfo(@ApiParam(name = "id", example = "1") @PathVariable(value = "id") @Min(value = 0) String id) {
        log.info("<-----------------getInfo请求被加载----------------->");
        return new Result<>(id);
    }

    @GetMapping("/heads")
    @ApiResponse(code = 200, message = "成功")
    public Result<String> getId(@RequestHeader("param") String p) {
        log.info("<-----------------getId请求被加载----------------->");
        return new Result<>(p);
    }

    @GetMapping(value = "/entities")
    @ApiResponse(code = 200, message = "成功")
    public Result<Person> getEntities(@ApiParam(name = "person", value = "Person对象") @RequestBody @Validated Person p) {
        if (Objects.isNull(p)) {
            return new Result<>(HttpStatus.BAD_REQUEST.value(), "请求实体为null!", null);
        }
        return new Result<>(p);
    }

    @GetMapping(value = "/number1")
    public int getNumber1() {
        number = baseService.getLocalVariable(number);
        log.info("getNumber1获取的结果为:" + number);
        return ++number;
    }

    @GetMapping(value = "/number2")
    public int getNumber2() {
        number = baseService.getLocalVariable(number);
        log.info("getNumber2获取的结果为:" + number);
        return ++number;
    }

    @PostMapping("/collect")
    public Result<Set<String>> getSettingContent(@RequestBody Set<String> param) {
        log.info("param:{}", param);
        return new Result<>(param);
    }


}
