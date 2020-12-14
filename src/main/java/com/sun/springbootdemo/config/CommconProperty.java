package com.sun.springbootdemo.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/7/20 16:08
 */
//@Component
@Data   //matchIfMissing:缺少该配置属性时是否可以加载。如果为true，没有该配置属性时也会正常加载；反之则不会生效
//@ConditionalOnProperty(prefix = "common", name = "name")
@ConfigurationProperties(prefix = "citycode", ignoreInvalidFields = true)
@Log4j2
@PropertySource(value = "classpath:common.properties")
public class CommconProperty /*implements CommonApi*/ {
    
   /* private String name;

    private int size;*/

    private List<String> list = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();


    public void test() {
        log.info("CommconProperty类的test方法正在执行中......................");
    }

    public String test1(String arg) {
        log.info("CommconProperty类的test1方法正在执行中......................");
        return "sundz";
    }

    /*@Override*/
    public String test2(String arg) {
        log.info("CommconProperty类的test2方法正在执行中......................");
        return "hello world";
    }

}
