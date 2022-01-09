package com.sun.springbootdemo.controller;

import com.sun.springbootdemo.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 用户操作 </p>
 *
 * @author Sundz
 * @date 2022/1/8 19:16
 */
@Log4j2
@RestController
@RequestMapping("userOp")
public class LoginController {


    @GetMapping("login")
    public String login(HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String passWord = request.getHeader("passWord");
        if (StringUtils.isAnyBlank(userName, passWord)) {
            throw new RuntimeException("请添加用户名和密码!");
        }
        Map<String, String> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("passWord", passWord);
        String token = JwtUtils.genJwt(claims);
        log.info("JWT生成的token:{}", token);
        return token;
    }

}
