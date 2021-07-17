package com.sun.springbootdemo.config.bean;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * <p> 导入Bean </p>
 *
 * @author Sundz
 * @date 2021/5/30 12:07
 */
public class ImporSelectorConfiguration implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Set<String> annotationTypes = metadata.getAnnotationTypes();
        for (String s : annotationTypes) {
            Set<String> anImport = metadata.getMetaAnnotationTypes(s);
            System.out.println(anImport);
        }
       /* MergedAnnotations annotations = metadata.getAnnotations();
        String className = metadata.getClassName();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AnimalService.class.getName());
        registry.registerBeanDefinition("animalService", builder.getBeanDefinition());*/
    }
}
