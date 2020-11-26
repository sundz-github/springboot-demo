package com.sun.springbootdemo.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Sundz
 * @since 2020/10/20 11:26
 */
@RequestMapping("/api")
@Api(tags = "ApiController")
public interface ApiController {

    @GetMapping(value = "/test")
    @ApiResponse(code = 200, message = "请求成功", response = String.class)
    String test(@RequestParam("param") String param);

    /* @ApiImplicitParams({
             // 过期时间，密码，别名，uid
             @ApiImplicitParam(name = "expireTime", value = "过期时间,格式:yyyy-MM:dd HH:mm:ss", required = true, dataType = "string"),
             @ApiImplicitParam(name = "passWord", value = "证书解密密码", required = true, dataType = "string"),
             @ApiImplicitParam(name = "cerAlias", value = "证书别名", dataType = "string"),
             @ApiImplicitParam(name = "uid", value = "用户id", required = true, dataType = "string")
     })*/
    @GetMapping(value = "/body")
    @ApiResponses({
            @ApiResponse(code = 203, message = "数据返回成功", examples = @Example(value = @ExampleProperty(value = "uid:sundz", mediaType = "application/json"))),
            @ApiResponse(code = 400, message = "请求错误")
    })
    public List<Map<String, String>> getbody(@RequestBody List<Map<String, String>> param);

    public Pair<String, Object> pair(@RequestParam("param") String param);
}
