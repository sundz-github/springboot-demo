package com.sun.springbootdemo.service.callback;

import javafx.util.Pair;
import lombok.Data;

import java.util.Random;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/8 9:50
 */
@Data
public class Store {

    private String storeName;

    public Store(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @param order 回调方法  回调购买类中的方法
     * @return {@link String}
     */
    public Pair<String, Object> getGoodsInfo(Order order) {
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(3);
        String[] stateArr = {"预定中", "发货中", "运输中"};
        // 回调购买者的方法
        String state = order.getOrderInfo(stateArr[index]);
        return new Pair<>(storeName, state);
    }

}
