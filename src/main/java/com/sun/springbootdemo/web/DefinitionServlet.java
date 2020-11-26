package com.sun.springbootdemo.web;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/21 21:03
 */
//@WebServlet(urlPatterns = {"/app/*"})  // 为啥没有生效呢？
@Log4j2
public class DefinitionServlet extends HttpServlet {

    private static final long serialVersionUID = 2162684362292591131L;

    /**
     * 处理get请求
     *
     * @param req
     * @param resp
     * @return void
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("<-------------------进入自定义DefinitionServlet容器执行《doGet》方法------------------->");
        //super.doPost(req, resp);  // 会报400错
        String uri = StringUtils.substringAfter(req.getRequestURI(), "app");  // /app/v1/test/10
        // 将请求转发到指定的Controller层
        req.getRequestDispatcher(uri).forward(req, resp);
        //resp.sendRedirect("/v1/test");
    }

    /**
     * 处理post请求
     *
     * @param req
     * @param resp
     * @return void
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("<-------------------进入自定义DefinitionServlet容器执行《doPost》方法------------------->");
        //super.doPost(req, resp);
    }
}
