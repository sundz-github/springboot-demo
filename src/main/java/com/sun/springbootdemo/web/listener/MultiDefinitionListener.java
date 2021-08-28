package com.sun.springbootdemo.web.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义监听器
 * ApplicationListener -->> 监听单一事件
 * SmartApplicationListener  -->> 监听多事件
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/18 11:20
 */
@Component
@Order
@Log4j2
public class MultiDefinitionListener implements SmartApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("<---------监听器生效了--------->");
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationStartedEvent.class.isAssignableFrom(eventType) ||
                ApplicationPreparedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
