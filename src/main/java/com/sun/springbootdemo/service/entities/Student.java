package com.sun.springbootdemo.service.entities;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.Serializable;

/**
 * @Description: TODO
 * @ClassName: Student
 * @Author: Sundz
 * @Date: 2020/6/5 10:53
 * @Version: V1.0
 **/
@Log4j2
@Data
public class Student implements Condition, Serializable {

    public Integer get(Student stu) {
        return stu.getAge();
    }

    private String name;

    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private static final long serialVersionUID = 1208495277170916303L;

    public Student() {
        log.info("Student构造方法已调用!");
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enviromentStr = context.getEnvironment().getProperty("os.name");
        return !StringUtils.isBlank(enviromentStr) && enviromentStr.contains("Windows");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
