package com.sun.springbootdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p> 公有属性 </p>
 *
 * @author Sundz
 * @date 2020/10/27 11:18
 */
@Component
public class ConfigProperty {

    @Value(value = "${com.sundz.id:-1}")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
