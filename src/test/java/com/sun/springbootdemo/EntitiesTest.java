package com.sun.springbootdemo;

import com.google.common.base.CharMatcher;
import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.config.ConfigProperty;
import com.sun.springbootdemo.service.MockInterface;
import com.sun.springbootdemo.service.TR;
import com.sun.springbootdemo.service.TestService;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/25 16:19
 */

@SpringBootTest(classes = {com.sun.springbootdemo.SpringbootdemoApplication.class})
@RunWith(SpringRunner.class)
@Log4j2
public class EntitiesTest {

    @MockBean
    private MockInterface mockInterface;

    @MockBean
    private TestService testService;

    @Autowired
    private TR tr;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private ConfigProperty configProperty;

    @Autowired
    private Environment environment;

    @Autowired
    ConfigurableApplicationContext context;

    @Before
    public void before() {
        log.info("<------------------------------------------------方法开始执行------------------------------------------------>");
    }

    @After
    public void after() {
        log.info("<------------------------------------------------方法执行结束------------------------------------------------>");
    }

    @Test
    public void mockTest() {
        Mockito.when(mockInterface.mock(Mockito.anyString())).thenReturn("Hello World");
        /* Mockito.when(testService.mock(Mockito.anyString(), Mockito.anyInt())).thenReturn("Hello World");*/
        System.out.println(mockInterface.mock(""));
    }

    @Test
    public void proprtyTest() {
        /*String property = environment.getProperty("spring.datasource.cic.jdbc-url");
        System.out.println(property);
        Multimap<String, String> multimap = HashMultimap.create();
        multimap.put("1", "A");
        multimap.put("2", "B");
        multimap.put("1", "C");
        multimap.put("1", "D");
        multimap.put("2", "E");
        multimap.put("3", "F");
        System.out.println(multimap.get("1"));*/
        String str = "1jihu1,ih8927u8.12908&ioji  ojidhjie890iojidhji ";
        /*Pattern p = Pattern.compile("\\d+");
        Matcher matcher = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (!matcher.find(1)) {
            matcher.appendReplacement(sb, "");
        }
        StringBuffer stringBuffer = matcher.appendTail(sb);
        System.out.println(stringBuffer);*/
        String s = CharMatcher.javaDigit().retainFrom(str);
        System.out.println(s);
    }

    @Test
    public void contextTest() {
    }

}
