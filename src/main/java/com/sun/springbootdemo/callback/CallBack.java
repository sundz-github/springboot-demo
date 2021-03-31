package com.sun.springbootdemo.callback;

/**
 * <p> 回调接口 </p>
 * 1、B类的方法调用A类的回调方法，A类调用B类的方法
 * 2、 当前方法有一段逻辑，需要调用者来决定怎么执行。这段逻辑肯定不能写死，所以需要一个接口，来解耦当前方法和调用者；
 *
 * @author Sundz
 * @date 2021/1/25 14:19
 */
public interface CallBack {
    void callBack(String param);
}
