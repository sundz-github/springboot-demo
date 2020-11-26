package com.sun.springbootdemo.service.exceptions;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/7 13:54
 */

public class EntitiesException extends RuntimeException {

    private static final long serialVersionUID = 5979810545091062039L;

    public EntitiesException() {
        super("自定义实体异常!");
    }
}
