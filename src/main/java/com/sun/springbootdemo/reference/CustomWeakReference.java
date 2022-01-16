package com.sun.springbootdemo.reference;

import com.sun.springbootdemo.entities.Person;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * <p> 弱引用 </p>
 *
 * @author Sundz
 * @date 2021/1/6 20:13
 */
@Log4j2
public class CustomWeakReference {

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
        for (int i = 0; i < 100; i++) {
            Person person = new Person((Long.valueOf(String.valueOf(i + 1))), "夏侯惇", 33, "m", false, new Date(), null);
            WeakReference<Person> personWeakReference = new WeakReference<>(person, queue);
            person = null;
            log.info("当前人数:{}", i + 1);
            System.gc();
            Person p = personWeakReference.get();
            log.info("Person:{}", p);
        }
        System.gc();
    }


}
