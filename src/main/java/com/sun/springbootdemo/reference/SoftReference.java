package com.sun.springbootdemo.reference;

import com.sun.springbootdemo.entities.Person;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * <p> 软引用 </p>
 *
 * @author Sundz
 * @date 2021/1/6 18:55
 */
@Log4j2
public class SoftReference {
    @Test
    public void softTest() {
        Person p = new Person("夏侯惇", 33, "m", false, new Date(), null);
        java.lang.ref.SoftReference<Person> softReference = new java.lang.ref.SoftReference<>(p);
        // 使其能被垃圾回收期识别 回收
        p = null;
        // 通知来垃圾回收器进行回收
        System.gc();
        log.info("内存充足时:{}", softReference.get());
        // 模拟其他的程序占用内存  该出我设置的堆最大内存为10M（-Xmx10m）
        byte[] bytes = new byte[1024 * 600 * 7];
        // 再一次通知GC进行垃圾回收
        System.gc();
        // 此时已因为内存不足 对象已被回收
        log.info("内存不足时:{}", softReference.get());

    }
}
