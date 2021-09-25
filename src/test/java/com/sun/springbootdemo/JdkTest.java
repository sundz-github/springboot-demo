package com.sun.springbootdemo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/9/11 20:30
 */
public class JdkTest extends BaseJnuit5Test {

    @Test
    @SneakyThrows
    public void inpuStreamTest() {
        String s = "1234@小明";
        System.out.println(s.substring(s.indexOf("@") + 1));

    }


    @Test
    public void bufferTest() {
        /*InputStream resourceAsStream = JdkTest.class.getResourceAsStream("/picture/动画.jpg");
        InputStream resourceAsStream1 = JdkTest.class.getClassLoader().getResourceAsStream("picture/动画.jpg");
        System.out.println(resourceAsStream);*/
        File file = new File("picture/动画.jpg");
        // E:\idea_workplace\personal\springbootdemo\picture\动画.jpg
        System.out.println(file.getAbsolutePath());
    }

}
