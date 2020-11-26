package com.sun.springbootdemo.service.callback;

import javafx.util.Pair;
import lombok.Data;

/**
 * @description:
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/8 9:49
 */
@Data
public class Buyer implements Order {

    private Store store;

    private String goodsName;

    private String buyerName;

    public Buyer(Store store, String goodsName, String buyerName) {
        this.store = store;
        this.goodsName = goodsName;
        this.buyerName = buyerName;
    }

    /**
     * @param state 获取商品预定信息
     * @return {@link String} 回复实体
     */
    @Override
    public String getOrderInfo(String state) {
        return state;
    }

    /**
     * 外部主调方法
     *
     * @param
     * @return void
     */
    public void order() {
        // 调用商店方法（B类）
        Pair<String, Object> goodsInfo = store.getGoodsInfo(this);
        System.out.println("购买者:" + buyerName);
        System.out.println("商家:" + goodsInfo.getKey());
        System.out.println("商品名称:" + goodsName);
        System.out.println("货物状态:" + goodsInfo.getValue());
    }

}
