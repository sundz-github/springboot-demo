package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/18 11:06
 */
@Log4j2
public class CustomizeWebFilter implements Filter {

    private FilterConfig filterConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @field: Spring中指读取web.xml相关配置，主要设置过滤器相关信息
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        Enumeration<String> enumeration = filterConfig.getInitParameterNames();


    }

    @Override
    public void destroy() {

    }

    /**
     * 调用链
     *
     * @param request
     * @param response
     * @param chain
     * @return void
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("filter is processing................" + filterConfig.getInitParameter("url"));
        chain.doFilter(request, response);
    }

}
