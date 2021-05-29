package com.sun.springbootdemo.mapper;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/5/15 17:34
 */
public class UserMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("调用之前");
        invocation.proceed();
        System.out.println("调用后");
        return "Hello";
    }
}
