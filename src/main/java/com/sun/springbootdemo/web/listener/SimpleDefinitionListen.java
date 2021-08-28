package com.sun.springbootdemo.web.listener;

import com.sun.springbootdemo.web.DefinitionEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description: 单一事件监听器
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/18 12:46
 */
@Component
//@WebListener
@Log4j2
public class SimpleDefinitionListen implements ApplicationListener<DefinitionEvent> {

    @Override
    public void onApplicationEvent(DefinitionEvent event) {
        Object source = event.getSource();
        log.info("《DefinitionEvent》自定义事件被《SimpleDefinitionListen》监听到了-->>" + source);
    }

}
