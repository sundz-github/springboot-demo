package com.sun.springbootdemo;

import com.sun.springbootdemo.web.DefinitionEvent;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@MapperScan("com.sun.springbootdemo.mapper")
@EnableSwagger2
//需要制定监听器扫描路径
@ServletComponentScan("com.sun.springbootdemo.web")
@EnableCaching
public class SpringbootdemoApplication {


    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringbootdemoApplication.class);
        //springApplication.addInitializers(new DefinitionInitializer());
        //springApplication.addListeners(new SimpleDefinitionListen()); // -- >相当于向容器注册bean
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        applicationContext.publishEvent(new DefinitionEvent("我是定义事件!"));
        log.info("<---------------SpringbootdemoApplication工程正在启动---------------o>");

    }


}
