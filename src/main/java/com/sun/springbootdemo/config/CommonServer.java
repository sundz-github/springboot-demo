package com.sun.springbootdemo.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p> 公共服务 </p>
 *
 * @author Sundz
 * @date 2020/12/3 13:44
 */
public class CommonServer implements ImportSelector {

    /**
     * 将CommonServer类注入Spring容器
     *
     * @param importingClassMetadata
     * @return {@link String[]}
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{CommonServer.class.getName()};
    }
}
