package com.sun.springbootdemo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 测试Spring Security </p>
 *
 * @author Sundz
 * @date 2021/4/11 10:47
 */
@RestController
@Log4j2
public class SecurityController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin")
    public String admin() {
        log.info("<---------------security---admin------------------>");
        return "admin";
    }

    @GetMapping("normal")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public String normal() {
        log.info("<---------------security---normal------------------>");
        return "normal";
    }

    @GetMapping("no")
    public String no() {
        log.info("<---------------security---admin------------------>");
        return "no authorize";
    }
}
