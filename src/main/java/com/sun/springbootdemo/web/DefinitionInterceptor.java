package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 执行顺序： preHandle -> 控制器（contorller）-> postHandle -> 页面渲染 -> afterCompletion
 *
 * @date: 2020/10/13 18:18
 */
@Log4j2
public class DefinitionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            log.info("到达控制器之前的操作！");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String queryString = request.getQueryString();
            String param = request.getParameter("param");
            // 获取控制器
            Object bean = handlerMethod.getBean();
            String controllerName = ((HandlerMethod) handler).getBeanType().getName();
            return true;
        }
        return true;

    }

    /**
     * @field 控制器发生异常时 不会执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("页面渲染之前操作！");
    }

    /**
     * @field 不管有没有异常  都会执行  且可以获取异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("页面渲染完毕，执行某些资源释放动作!");
    }
}
