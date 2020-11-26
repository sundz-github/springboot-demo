package com.sun.springbootdemo.service.callback;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/8 10:29
 */
public class Test {

    public static void main(String[] args) {
        Store store = new Store("川沙中路麦当劳店");
        Buyer buyer = new Buyer(store, "2个汉堡+1杯可乐", "Tom");
        buyer.order();
    }

}
