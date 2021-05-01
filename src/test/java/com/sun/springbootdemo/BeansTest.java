package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.MarkAnnotation;
import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.entities.Student;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:16
 */
@MarkAnnotation(value = "hello", keys = {"1", "2"})
@Log4j2
public class BeansTest extends BaseJnuit5Test {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Autowired
    private XiaoMingImpl xiaoMing;


    @Test
    public void reflectTest() throws Exception {
        Class<BeansTest> clazz = BeansTest.class;
        MarkAnnotation markAnnotation = clazz.getAnnotation(MarkAnnotation.class);
        System.out.println(markAnnotation.value());

    }

    @Test
    public void serializableTest() throws Exception {
        /*Student stu = new Student("周瑜", 15);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("C:/Users/Administrator/Desktop/student.txt"));
        outputStream.writeObject(stu);
        log.info("写入成功!");
        outputStream.close();*/
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("C:/Users/Administrator/Desktop/student.txt"));
        Student student = (Student) inputStream.readObject();
        System.out.println(student);
        inputStream.close();
    }

}
