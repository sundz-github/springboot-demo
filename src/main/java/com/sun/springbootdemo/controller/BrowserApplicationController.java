package com.sun.springbootdemo.controller;


import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.sun.springbootdemo.annotation.RequestLog;
import com.sun.springbootdemo.dto.UserDTO;
import com.sun.springbootdemo.entities.Person;
import com.sun.springbootdemo.entities.Record;
import com.sun.springbootdemo.entities.Result;
import com.sun.springbootdemo.entities.RoleEnum;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.rabbitmq.RabitMqProducer;
import com.sun.springbootdemo.retry.RetryService;
import com.sun.springbootdemo.service.database.UserService;
import com.sun.springbootdemo.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private UserService userService;

    @Autowired
    private RetryService retryService;

    @Autowired
    private RabitMqProducer rabitMqProducer;


    @ApiOperation(value = "测试方法", notes = "test方法")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @SneakyThrows

    public String test(HttpServletRequest request, HttpServletResponse response) {
        BufferedReader reader = request.getReader();
        String content = null;
        StringBuilder builder = new StringBuilder();
        while ((content = reader.readLine()) != null) {
            builder.append(content).append("\n");
        }
        return builder.toString();
    }

    /**
     * @Description: 采用form_data表单
     * @Param: [data]
     * @Return: java.lang.String
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/7/31 13:53
     */
    @RequestMapping(value = "async", method = RequestMethod.POST)
    public String async(@RequestParam(name = "type") Integer type) {
        CompletableFuture<String> asyncFuture = CompletableFuture.supplyAsync(() -> {
            try {
                if (type == 1) {
                    throw new RuntimeException("异常来啦!");
                }
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            System.out.println("异步线程：" + Thread.currentThread().getName());
            return "This is async result！";
        });
        asyncFuture.whenComplete((a, b) -> {
            System.out.println("whenComplete获取的结果:" + a);
            if (!Objects.isNull(b)) {
                b.printStackTrace();
            }
            System.out.println("whenComplete异步线程：" + Thread.currentThread().getName());
        });
        // 正常回调
        CompletableFuture<String> callback = asyncFuture.thenApplyAsync(result -> {
            System.out.println("回调线程：" + Thread.currentThread().getName());
            return "This is callback result！";
        }, executorService);
        /*// 异常回调
        CompletableFuture<String> exceCallBack = callback.exceptionally(e -> {
            try {
                System.out.println("异常回调线程：" + Thread.currentThread().getName());
                throw new RuntimeException(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return "This is exception result！";
        });*/
        System.out.println("主线程:" + Thread.currentThread().getName());
        return "success";
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

    @GetMapping("exportExcel")
    public void downLoad(HttpServletResponse response) throws IOException {

        String outFile = "用户-" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()) + ".xlsx";
        //设置返回的文件类型
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //对文件编码
        outFile = response.encodeURL(new String(outFile.getBytes("gb2312"), "iso8859-1"));
        //使用Servlet实现文件下载的时候，避免浏览器自动打开文件
        response.addHeader("Content-Disposition", "attachment;filename=" + outFile);
        EasyExcel.write(response.getOutputStream(), UserDTO.class).sheet("用户信息").doWrite(userService.queryAll());
    }

    @PostMapping("imporExcel")
    @SneakyThrows
    public void imporData(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new FileNotFoundException("上传文件名不存在!");
        }
        Consumer<List<UserDTO>> action = x -> {
            List<User> users = new ArrayList<>();
            for (UserDTO dto : x) {
                User user = new User();
                BeanUtils.copyProperties(dto, user);
                String roleEnum = dto.getRoleEnum();
                if (StringUtils.isNotBlank(roleEnum) && roleEnum.equals("ADMINSTRATION")) {
                    user.setRoleEnum(RoleEnum.ADMINSTRATION);
                } else {
                    user.setRoleEnum(RoleEnum.NORMAL);
                }
                users.add(user);
            }
            userMapper.insertBatch(users);
        };
        EasyExcel.read(file.getInputStream(), UserDTO.class, ExcelUtils.getReadListener(action, 5)).sheet().headRowNumber(0).doRead();
    }

    @GetMapping("retry")
    public String retry(@RequestParam("id") String id) {
        retryService.apply(id);
        return "retry";
    }

    @GetMapping("downLoad")
    public String downLoad(HttpServletRequest request, @RequestBody String parm) throws Exception {
        /*request.setAttribute("userId", "10000");
        ResponseWrapper responseWrapper = new ResponseWrapper(12L, response);
        String s = new String(responseWrapper.toByteArray(), StandardCharsets.UTF_8);*/
        /*request.getRequestDispatcher("/noLogin/test2").forward(request, response);*/
        return parm;
    }


    @PostMapping("rabbitmq")
    public String redirect(@RequestBody String p) throws Exception {
        //rabitMqProducer.send(objectMapper.writeValueAsString(p));
        rabitMqProducer.send(objectMapper.writeValueAsString(p));
        return "OK";
    }

}
