package com.sun.springbootdemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.sun.springbootdemo.annotation.RequestLog;
import com.sun.springbootdemo.entities.Person;
import com.sun.springbootdemo.entities.Record;
import com.sun.springbootdemo.entities.Result;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
@RequestMapping(value = "noLogin")
public class BrowserApplicationController {

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    private UserMapper userMapper;

    @ApiOperation(value = "测试方法", notes = "test方法")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result<String> test(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "param") String t) {
        log.info("getRequestURL:" + request.getRequestURI());  //URL -->> http://localhost:8080/v1/test  URI -->> /v1/test
        Map<String, String[]> parameterMap = request.getParameterMap();
        return new Result<>("success");
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
    @RequestLog(type = 1, remark = "getEntity的请求日志")
    //@ApiImplicitParam(value = "传入字符串", name = "name", example = "Hello World", required = true, dataType = "string", paramType = "query")
    public ResponseEntity<Person> getEntity(@ApiParam(name = "person", value = "person对象") @Validated @RequestBody Person p) {
        if (Objects.isNull(p)) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
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

    @GetMapping(value = "records")
    public Result<Record> record(@RequestBody Record record) throws Exception {
        String s = objectMapper.writeValueAsString(record);
        Map r = objectMapper.readValue(s, Map.class);
        Object uuid = r.get("uuid");
        return new Result.Builder<Record>().success(record).build();
    }

    @GetMapping("exportData")
    public void downLoad(HttpServletResponse response) throws IOException {
        List<User> users = userMapper.selectAll();
        String[] heads = {"id", "用户名", "密码", "年龄", "角色", "更新时间"};
        Workbook workbook = ExcelUtils.exportData(heads, users);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        String outFile = "用户-" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + ".xls";
        //设置返回的文件类型
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //对文件编码
        outFile = response.encodeURL(new String(outFile.getBytes("gb2312"), "iso8859-1"));
        //使用Servlet实现文件下载的时候，避免浏览器自动打开文件
        response.addHeader("Content-Disposition", "attachment;filename=" + outFile);
    }

    @GetMapping("imporData")
    public List<User> imporData(@RequestParam("filePath") String filePath) {
        return ExcelUtils.importData(filePath);
    }

    @PostMapping("sundz")
    public String test() {
        return "sundz";
    }


}
