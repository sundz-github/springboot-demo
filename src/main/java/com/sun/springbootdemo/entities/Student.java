package com.sun.springbootdemo.entities;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

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
public class Student implements Serializable {


    private static final long serialVersionUID = -1831780557993099187L;
    // 1831780557993099187L
    private String name;

    private int age;

    private Integer sex;

    public Integer get(Student stu) {
        return stu.getAge();
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student() {
        log.info("Student构造方法已调用!");
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
