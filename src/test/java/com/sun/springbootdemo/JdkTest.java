package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.ColumnIndex;
import com.sun.springbootdemo.entities.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
        /*SpringBootTest annotation = AnnotationUtils.findAnnotation(BaseJnuit5Test.class, SpringBootTest.class);*/
        for (Field field : User.class.getDeclaredFields()) {
            ColumnIndex annotation = AnnotationUtils.findAnnotation(field, ColumnIndex.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            System.out.println(annotation.index());
        }

    }

    public Object getColumnContent(int columnIndex, User user) throws IllegalAccessException {
        Object result = null;
        for (Field field : User.class.getDeclaredFields()) {
            ColumnIndex annotation = AnnotationUtils.findAnnotation(field, ColumnIndex.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            if (annotation.index() == columnIndex) {
                result = field.get(user);
            }
        }
        return result;
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
