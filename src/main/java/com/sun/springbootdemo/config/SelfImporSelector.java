package com.sun.springbootdemo.config;

import com.sun.springbootdemo.entities.Student;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p> @Import注解导入容器 </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:36
 */
public class SelfImporSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Student.class.getName()};
    }
}
