package com.sun.springbootdemo.service.entities;

import com.google.common.base.MoreObjects;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/27 11:47
 */
@Service
public class Eagle implements Animals {
    private String name;
    private int age;
    private String habit;
    private String sex;

    @Override
    public String eat(String food) {
        return "nut";
    }

    public Eagle(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Eagle() {

    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Eagle.class).add("name", name).add("age", age).add("habit", habit).add("sex", sex).toString();
    }
}
