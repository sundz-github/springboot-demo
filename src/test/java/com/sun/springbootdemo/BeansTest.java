package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.MarkAnnotation;
import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.config.CommonServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
@MarkAnnotation(value = "hello", keys = {"1", "2"})
public class BeansTest extends BaseJnuit5Test {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommonServer commonServer;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Autowired
    private XiaoMingImpl xiaoMing;

    @Value("${para.scheduled.crons}")
    private List<String> crons;

    @Test
    public void test1() {
        CommonServer bean = context.getBean(CommonServer.class);
        RestTemplate build = restTemplateBuilder.build();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void callBackTest() {
        String priStr = "-----BEGIN CERTIFICATE-----MIGTAgopeeiojfdoejwiorfjwe5iorf0ewfpower-----END CERTIFICATE-----";
        String regex = "^[\\-]{5}[\\w ]+[\\-]{5}([^-]+)[\\-]{5}[\\w ]+[\\-]{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(priStr);
        if (matcher.find()) {
            String group = matcher.group(1);
            System.out.println(group);
        }
    }

    @Test
    public void reflectTest() throws Exception {
        Class<BeansTest> clazz = BeansTest.class;
        MarkAnnotation markAnnotation = clazz.getAnnotation(MarkAnnotation.class);
        System.out.println(markAnnotation.value());

    }

    @Test
    public void test() {
        Calendar todayCal = Calendar.getInstance();
        todayCal.set(Calendar.HOUR_OF_DAY, 0);
        todayCal.set(Calendar.MINUTE, 0);
        todayCal.set(Calendar.SECOND, 0);
        todayCal.set(Calendar.MILLISECOND, 0);
        Date todayDate = todayCal.getTime();
        System.out.println(todayDate);

    }

}
