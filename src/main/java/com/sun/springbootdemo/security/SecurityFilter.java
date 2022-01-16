package com.sun.springbootdemo.security;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/12 22:34
 */

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("<----------Security后置过滤器开始处理---------->");
        chain.doFilter(request, response);
    }
}
