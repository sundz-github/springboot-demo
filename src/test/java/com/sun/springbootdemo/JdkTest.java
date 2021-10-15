package com.sun.springbootdemo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
        ByteBuffer byteBuffer = ByteBuffer.wrap("你好".getBytes());
        /*byteBuffer.clear();*/
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.remaining());
        System.out.println(byteBuffer.capacity());
        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
    }

}
