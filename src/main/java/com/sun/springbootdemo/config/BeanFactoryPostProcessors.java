package com.sun.springbootdemo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: bean工厂处理方法  --  >> bean实例化前的处理
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/7/14 17:03
 */
@Configuration
@Log4j2
public class BeanFactoryPostProcessors implements BeanFactoryPostProcessor {
    

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        /*BeanDefinition beanDefinition = beanFactory.getBeanDefinition("record1");
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        //Record(110, "紧急求救")beanDefinition.getPropertyValues().add("id",120);
        beanDefinition.getPropertyValues().add("name", "急救电话");
        log.info("BeanFactoryPostProcessor接口处理完毕!");*/
    }
}
