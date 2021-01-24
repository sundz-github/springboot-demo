package com.sun.springbootdemo.web;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/10/13 18:18
 */
public class DefinitionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取控制器
        Object bean = handlerMethod.getBean();
        String controllerName = ((HandlerMethod) handler).getBeanType().getName();
        return false;
    }

    /**
     * @field 控制器发生异常时 不会执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * @field 不管有没有异常  都会执行  且可以获取异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
