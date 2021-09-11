package com.sun.springbootdemo;

import com.sun.springbootdemo.config.bean.ImporSelectorConfiguration;
import com.sun.springbootdemo.web.CustomDefinitionEvent;
import com.sun.springbootdemo.web.DefinitionInitializer;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class, SecurityAutoConfiguration.class})
@MapperScan("com.sun.springbootdemo.mapper")
@EnableSwagger2
//需要制定监听器扫描路径
@ServletComponentScan("com.sun.springbootdemo.web")
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableRetry
@Import({ImporSelectorConfiguration.class})
public class SpringbootdemoApplication {


    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringbootdemoApplication.class);
        springApplication.addInitializers(new DefinitionInitializer());
        //springApplication.addListeners(new SimpleDefinitionListen()); // -- >相当于向容器注册bean
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        applicationContext.publishEvent(new CustomDefinitionEvent("我是定义事件!"));
        log.info("<---------------SpringbootdemoApplication工程正在启动--------------->");
    }

}
