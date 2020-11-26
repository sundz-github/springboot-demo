package com.sun.springbootdemo.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/29 17:51
 */
@Service
public class BaseService {


    /**
     * 将变量与当前线程进行绑定
     *
     * @param v1
     * @return {@link Integer}
     */
    public Integer getLocalVariable(Integer v1) {
        ThreadLocal<Integer> numberThreadLocal = new ThreadLocal<>();
        if (v1 == null || numberThreadLocal.get() == null) {
            numberThreadLocal.set(0);
        }
        return numberThreadLocal.get();
    }
}
