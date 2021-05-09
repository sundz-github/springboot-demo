package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.MarkAnnotation;
import com.sun.springbootdemo.callback.XiaoMingImpl;
import com.sun.springbootdemo.config.CommconProperty;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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
        /*ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("C:/Users/Administrator/Desktop/student.txt"));
        Student student = (Student) inputStream.readObject();
        System.out.println(student);
        inputStream.close();*/
        String t = "a";
        System.out.println(t.toCharArray()[0]);
    }

    @Test
    public void test() {
        /*try {
            User user = new User();
            Method method = User.class.getDeclaredMethod("setId", Long.class);
            System.out.println(method.getParameters()[0].getType().getName());
            method.invoke(user, 1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // java.lang.Long
        /*String dataStr = "2021-05-09 21:42:20";
        TemporalAccessor parse = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(dataStr);
        LocalDateTime localDateTime = LocalDateTime.from(parse);
        System.out.println(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));*/
        

    }


}
