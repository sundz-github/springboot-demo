package com.sun.springbootdemo.config;

import com.sun.springbootdemo.service.entities.Record;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;


/**
 * @Description: Bean初始化的前后处理，默认处理所有的bean对象  -- >> bean对象刚创建 还没赋值
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/7/14 16:16
 */
@Configuration
@Log4j2
public class BeanPostProcessors implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Record) {
            ((Record) bean).setId(168);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /*if ("record1".equalsIgnoreCase(beanName)) {
            Record r = (Record) bean;
            r.setId(521);
            r.setName("我是521");
            System.out.println("BeanPostProcessor的<后>置处理器执行了--->>  " + r);
        }*/

        return bean;
    }
}
