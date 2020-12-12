package com.sun.springbootdemo;

import com.google.common.base.CharMatcher;
import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.config.ConfigProperty;
import com.sun.springbootdemo.service.MockInterface;
import com.sun.springbootdemo.service.TR;
import com.sun.springbootdemo.service.TestService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/25 16:19
 */


@Log4j2
public class MockTest extends BaseJnuit5Test {

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
