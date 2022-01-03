package com.sun.springbootdemo;

import com.sun.springbootdemo.annotation.ColumnIndex;
import com.sun.springbootdemo.entities.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
        Method[] methods = JdkTest.class.getDeclaredMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                /*parameter.*/
            }
        }
    }

}
