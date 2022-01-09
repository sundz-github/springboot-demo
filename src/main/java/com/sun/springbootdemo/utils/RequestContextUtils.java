package com.sun.springbootdemo.utils;

import com.sun.springbootdemo.entities.User;

/**
 * <p> 用户信息操作 </p>
 *
 * @author Sundz
 * @date 2022/1/8 20:33
 */
public class RequestContextUtils {

    private static final ThreadLocal<User> USERCONTEXT = new ThreadLocal<>();
    

    /**
     * @field 初始化用户信息
     */
    public static void setUserContext(User user) {
        USERCONTEXT.set(user);
    }


    /**
     * @field 获取用户信息
     */
    public static ThreadLocal<User> getUserContext() {
        return USERCONTEXT;
    }

    /**
     * @field 清除用户信息
     */
    public static void removeUserContext() {
        USERCONTEXT.remove();
    }

}
