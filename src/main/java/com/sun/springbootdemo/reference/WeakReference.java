package com.sun.springbootdemo.reference;

import com.sun.springbootdemo.entities.Person;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p> 弱引用 </p>
 *
 * @author Sundz
 * @date 2021/1/6 20:13
 */
@Log4j2
public class WeakReference {

    @Test
    public void weakRefereceTest() throws Exception {
        // 引用队列 对象呗回收时就会将weakReference加入引用队列
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        // 后台守线程  主要模仿垃圾回收机制（引用队列中有值则说明 对象已经被垃圾回收器回收）
        Thread daemon = new Thread(() -> {
            Reference<? extends Person> poll;
            while ((poll = queue.poll()) != null) {
                log.info("对象已经被回收:{}", poll.get());
            }
        });
        daemon.setDaemon(true);
        daemon.start();
        List<java.lang.ref.WeakReference<Person>> list = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            list.add(new java.lang.ref.WeakReference<>(new Person(1L, "夏侯惇", 33, "m", false, new Date(), null), queue));
            log.info("插入的个人数:{}", i + 1);
        }
        list = null;
        System.gc();
        //TimeUnit.SECONDS.sleep(2);
        //log.info("回收后:weakReference:{}", weakReference.get());


    }


}
