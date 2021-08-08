package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.MarkAnnotation;
import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import com.sundz.service.AnimalService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
@MarkAnnotation(value = "hello", keys = {"1", "2"})
@Log4j2
public class BeansTest extends BaseJnuit5Test {

    private int a;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Autowired
    private XiaoMingImpl xiaoMing;


    @Resource
    private AnimalService animalService;


    @Test
    public void reflectTest() throws Exception {
        

    }

    @Test
    public void proxyTest() {
        List<Integer> value = Arrays.asList(1, 2, 0, 7, 8);
        value.forEach(lambdaWrapper(it -> System.out.println("结果:" + 10 / it)));
    }

    @Test
    public void lambdaTest() {
        // 静态方法
        Function<String, Integer> fun = BeansTest::staticTest;
        // 非静态方法
        BiFunction<BeansTest, String, Integer> fun2 = new BiFunction<BeansTest, String, Integer>() {
            @Override
            public Integer apply(BeansTest p1, String p2) {
                return p1.noStaticTest(p2);
            }
        };

        BiPredicate<String, String> p1 = String::equals;
        Function<String, Integer> fun3 = this::noStaticTest;


    }

    public Integer noStaticTest(String s) {
        return Integer.parseInt(s);
    }

    public static Integer staticTest(String p) {
        return Integer.parseInt(p);
    }


    public <T> Consumer<T> lambdaWrapper(Consumer<T> consumer) {
        return it -> {
            try {
                consumer.accept(it);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
