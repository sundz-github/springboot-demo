package com.sun.springbootdemo.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/13 0:36
 */
public class SelfImporSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{CommonServer.class.getName()};
    }
}
