package com.sun.springbootdemo.config;

import com.github.pagehelper.PageHelper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/4 10:46
 */
@Configuration
@Log4j2
public class DataSourceConfig {

    /**
     * @Description: 数据库源配置-主库
     * @Param: []
     * @Return: javax.sql.DataSource
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/4 10:56
     */
    @Bean("dataSourceMaster")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.cic", ignoreInvalidFields = true)
    public DataSource dataSourceMaster() {
        //DataSourceBuilder.create().build();
        return new HikariDataSource();
    }

    /**
     * @Description: 数据库源配置-辅助库
     * @Param: []
     * @Return: javax.sql.DataSource
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/4 13:57
     */
    @Bean("dataSourceSlave")
    @ConfigurationProperties(prefix = "spring.datasource.cicfx", ignoreInvalidFields = true)
    public DataSource dataSourceSlave() {
        return new HikariDataSource();
    }

    /**
     * @Description: 事务管理器配置-主
     * @Param: [dataSource]
     * @Return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/4 10:56
     */
    @Bean("dataSourceTransactionManagerMaster")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManagerMaster(@Autowired @Qualifier("dataSourceMaster") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 分页相关配置
     *
     * @param
     * @return {@link PageHelper}
     */
    /*@Bean
    PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count = countSql");
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }*/

    /**
     * @Description: 事务管理器配置-辅助
     * @Param: [dataSource]
     * @Return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/4 13:58
     */
    @Bean("dataSourceTransactionManagerSlave")
    public DataSourceTransactionManager dataSourceTransactionManagerSlave(@Autowired @Qualifier("dataSourceSlave") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 多数据源时，因@Order顺序不同，如果不手动配置将会导致application.properties失效
     * mybatis相关的配置都会被绑定到这个bean
     *
     * @param
     * @return {@link org.apache.ibatis.session.Configuration}
     */
    @Bean("config")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration getConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 指定 MyBatis 所用日志的具体实现，未指定时将自动查找,未查找到将关闭日志
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        return new org.apache.ibatis.session.Configuration();
    }

    /**
     * @Description: 会话工厂对象-主
     * @Param: [dataSource]
     * @Return: org.apache.ibatis.session.SqlSessionFactory
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/5 16:06
     */
    @Primary
    @Bean("sessionFactoryMaster")
    public SqlSessionFactory sessionFactoryMaster(@Autowired @Qualifier("dataSourceMaster") DataSource dataSource, @Autowired @Qualifier("config") org.apache.ibatis.session.Configuration configuration
    ) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加载Mybatis的配置
        bean.setConfiguration(configuration);
        //bean.setPlugins(new Interceptor[]{pageHelper});
        return bean.getObject();
    }

    /**
     * @Description: 会话工厂对象-辅助
     * @Param: [dataSource]
     * @Return: org.apache.ibatis.session.SqlSessionFactory
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/5 16:12
     */
    @Bean("sessionFactorySlave")
    public SqlSessionFactory sessionFactorySlave(@Autowired @Qualifier("dataSourceSlave") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    /**
     * @Description: 会话模板-主
     * @Param: [sqlSessionFactory]
     * @Return: org.mybatis.spring.SqlSessionTemplate
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/5 16:14
     */
    @Primary
    @Bean("sqlSessionTemplateMaster")
    public SqlSessionTemplate sqlSessionTemplateMaster(@Autowired @Qualifier("sessionFactoryMaster") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * @Description: 会话模板-辅助
     * @Param: [sqlSessionFactory]
     * @Return: org.mybatis.spring.SqlSessionTemplate
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/5 16:16
     */
    @Bean("sqlSessionTemplateSlave")
    public SqlSessionTemplate sqlSessionTemplateSlave(@Autowired @Qualifier("sessionFactorySlave") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
