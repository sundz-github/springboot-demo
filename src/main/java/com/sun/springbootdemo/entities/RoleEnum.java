package com.sun.springbootdemo.entities;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/4/11 10:00
 */

public enum RoleEnum {
    NORMAL(1, "normal"),
    ADMINSTRATION(0, "administrator");
    private int order;
    private String role;

    RoleEnum(int order, String role) {
        this.order = order;
        this.role = role;
    }

    public int getOrder() {
        return order;
    }

    public String getRole() {
        return role;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

