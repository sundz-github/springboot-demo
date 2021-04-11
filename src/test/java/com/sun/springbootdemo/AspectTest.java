package com.sun.springbootdemo;

import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p> 切面测试 </p>
 *
 * @author Sundz
 * @date 2020/12/12 12:43
 */
public class AspectTest extends BaseJnuit5Test {

    @Autowired
    private CommconProperty commconProperty;

    @Test
    public void afterReturningTest() {
        String result = commconProperty.test2("Hello World");
    }

    @Test
    public void uuidTest() {
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void test2() {

        List<Student> stuList = new ArrayList<>();
        stuList.add(new Student("夏侯惇", 31));
        stuList.add(new Student("不知火舞", 24));
        stuList.add(new Student("孙尚香", 27));
        stuList.add(new Student("曹操", 36));
        stuList.add(new Student("周瑜", 32));
        // 非静态方法的函数引用（Student::getName）
        List<String> stuNameList = stuList.stream().map(Student::getName).collect(Collectors.toList());
    }

    @Test
    public void test3() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }


}
