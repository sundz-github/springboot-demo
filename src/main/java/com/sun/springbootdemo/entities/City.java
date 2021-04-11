package com.sun.springbootdemo.entities;

import lombok.Data;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/21 10:47
 */
@Data
public class City {

    private int id;

    private String cicCity;

    private String cicMail;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cicCity='" + cicCity + '\'' +
                ", cicMail='" + cicMail + '\'' +
                '}';
    }
}
