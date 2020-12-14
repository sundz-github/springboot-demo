package com.sun.springbootdemo.config;


import com.sun.springbootdemo.service.entities.Record;
import com.sun.springbootdemo.service.entities.Student;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @describtion: Bean自定义配置
 * @author: Sundz
 * @date: 2020/5/9 22:56
 */
@EnableConfigurationProperties(CommconProperty.class)  //可以使配置生效但是不会向容器注入bean
@Configuration(value = "configuration")
@Import({SelfImporSelector.class})
public class BeansConfig implements BeanDefinitionRegistryPostProcessor {

    @Bean(name = "record")
    @ConditionalOnMissingBean(name = "record")
    public Record record2() {
        return new Record(119, "火警求救电话");
    }

    /**
     * @Description: 将配置文件的属性加载到对象
     * @Param: []
     * @Return: org.apache.commons.configuration.Configuration
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/7 17:28
     */
    @Bean("propertiesConfiguration")
    public org.apache.commons.configuration.Configuration configuration() throws ConfigurationException {
        // 将指定配置文件加载到Configuration对象
        return new PropertiesConfiguration("common.properties");
    }

    /**
     * 动态向Spring注入bean
     *
     * @param registry
     * @return void
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition beanDefinition = new RootBeanDefinition(Student.class);
        registry.registerBeanDefinition("student", beanDefinition);
    }

    /**
     * BeanFactoryPostProcessor接口的Bean工厂方法
     *
     * @param beanFactory
     * @return void
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition stuBeanDefinition = beanFactory.getBeanDefinition("student");
        stuBeanDefinition.getPropertyValues().add("name", "夏侯惇").add("age", 30);
    }
}
