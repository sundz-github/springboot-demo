package com.sun.springbootdemo.web;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 自定义事件
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/18 12:44
 */
public class CustomDefinitionEvent extends ApplicationEvent {

    private static final long serialVersionUID = 7906224546750972091L;

    public CustomDefinitionEvent(Object source) {
        super(source);
    }


}
