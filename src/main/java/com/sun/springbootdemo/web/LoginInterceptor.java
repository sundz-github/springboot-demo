package com.sun.springbootdemo.web;

import com.sun.springbootdemo.utils.RequestContextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 执行顺序： preHandle -> 控制器（contorller）-> postHandle -> 页面渲染 -> afterCompletion
 *
 * @date: 2020/10/13 18:18
 */
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*if (handler instanceof HandlerMethod) {
            log.info("到达控制器之前的操作！");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            String type = request.getParameter("type");
            // 获取控制器
            Object bean = handlerMethod.getBean();
            String controllerName = ((HandlerMethod) handler).getBeanType().getName();
            return true;
        }*/
        /*String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization) && !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("登陆失败，请重新登录!");
        }

        String token = authorization.substring(7);
        try {
            Map<String, String> tokenMap = JwtUtils.parseToken(token);
            if (StringUtils.isBlank(token) || MapUtils.isEmpty(tokenMap)) {
                throw new RuntimeException("token校验不通过,请重新登录!");
            }
            String userName = tokenMap.get("userName");
            String passWord = tokenMap.get("passWord");
            User user = new User();
            user.setUserName(userName);
            user.setPassWord(passWord);
            log.info("用户信息加入上下文 user:{}", user);
            RequestContextUtils.setUserContext(user);
        } catch (Exception e) {
            throw new RuntimeException("token校验不通过,请重新登录!");
        }*/
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
        RequestContextUtils.removeUserContext();
        log.info("页面渲染完毕，用户信息从上下文清除!");
    }
}
