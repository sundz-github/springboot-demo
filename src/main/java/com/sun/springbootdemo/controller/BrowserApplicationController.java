package com.sun.springbootdemo.controller;


import com.google.common.collect.ImmutableMap;
import com.sun.springbootdemo.service.entities.Person;
import com.sun.springbootdemo.service.entities.Pet;
import com.sun.springbootdemo.service.entities.Result;
import com.sun.springbootdemo.service.exceptions.EntitiesException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @describtion: 控制类
 * @author: Sundz
 * @date: 2020/5/2 13:26
 */
@RestController
@Log4j2
@Api(value = "Controller控制器", tags = {"BrowserApplicationController"})
@RequestMapping(value = "/v1")
public class BrowserApplicationController {

    @ApiOperation(value = "测试方法", notes = "test方法")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result<String> test(HttpServletRequest request, HttpServletResponse response) {
        log.info("getRequestURL:" + request.getRequestURI());  //URL -->> http://localhost:8080/v1/test  URI -->> /v1/test
        return new Result<>("");
    }

    /**
     * @Description: 采用form_data表单
     * @Param: [data]
     * @Return: java.lang.String
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/7/31 13:53
     */
    @RequestMapping(value = "/formData", method = RequestMethod.POST)
    public String abc(@RequestParam String data) {
        return data;
    }


    @PostMapping(value = "/entities")
    //@ApiImplicitParam(value = "传入字符串", name = "name", example = "Hello World", required = true, dataType = "string", paramType = "query")
    public ResponseEntity<Person> getEntity(@ApiParam(name = "person", value = "person对象") @Validated @RequestBody Person p) {
        if (Objects.isNull(p)) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    @PostMapping(value = "v1/pets")
    public ResponseEntity<Pet> getPetEntities(@RequestBody(required = false) Pet p) {
        if (Objects.isNull(p) || StringUtils.isAnyBlank(p.getName(), p.getSex())) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(value = "/exception")
    public String handleException(@RequestParam("userid") String userId) {
        log.info("userId -->> " + userId);
        if (StringUtils.isBlank(userId)) {
            throw new EntitiesException();
        }
        return "hello world!";
    }


    //@ApiImplicitParam(name = "parameter2", value = "请求参数2", required = true, dataType = "string", paramType = "query", example = "你好")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", dataType = "string", paramType = "query", example = "13802780104", required = true),
            @ApiImplicitParam(name = "user_name", value = "登录账号", dataType = "string", paramType = "query", example = "lihailin9073", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", dataType = "string", paramType = "query", example = "123456", required = true),
            @ApiImplicitParam(name = "validate_code", value = "注册验证码", dataType = "string", paramType = "query", example = "3679", required = true)})*/
    @GetMapping(value = "/query")
    public Map<String, Object> query(@RequestParam(value = "parameter1", defaultValue = "Hello World") String parameter1, @ApiIgnore String parameter2) {
        return ImmutableMap.of("parameter1", parameter1, "parameter2", StringUtils.defaultIfBlank(parameter2, "parameter2"));
    }

}
