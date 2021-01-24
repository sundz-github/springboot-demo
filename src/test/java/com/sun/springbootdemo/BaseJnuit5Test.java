package com.sun.springbootdemo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <p> 测试基类 </p>
 *
 * @author Sundz
 * @date 2020/12/11 18:07
 */
@SpringBootTest(classes = {com.sun.springbootdemo.SpringbootdemoApplication.class})
@ExtendWith(SpringExtension.class)
@Rollback(value = false)
@Log4j2
public class BaseJnuit5Test {

    @BeforeEach
    public void before() {
        log.info("<---------------------方法开始执行--------------------->");
    }

    @AfterEach
    public void after() {
        log.info("<---------------------方法执行结束--------------------->");
    }


}
