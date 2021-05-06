package com.sun.springbootdemo.entities;

import com.sun.springbootdemo.mybatis.EnumTypeHandle;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/4/11 10:00
 */
@Getter
@AllArgsConstructor
public enum RoleEnum implements EnumTypeHandle {

    NORMAL(1, "normal"),

    ADMINSTRATION(0, "administrator");

    private int order;

    private String role;

    @Override
    public int order() {
        return getOrder();
    }
}

