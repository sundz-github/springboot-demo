package com.sun.springbootdemo.mybatis.intercepter;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * <p> Mybatis拦截器 </p>
 *
 * @author Sundz
 * @date 2020/12/8 16:57
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class,
                        CacheKey.class,
                        BoundSql.class})
        }
)
@Log4j2
public class MybatisIntercepter implements Interceptor {

    public MybatisIntercepter() {
        log.info("Mybatis自定义拦截器生效了!");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 开启二级缓存
        properties.setProperty("cacheEnabled", "true");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        SqlSource sqlSource = ms.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(args[1]);
        Field sqlField = BoundSql.class.getDeclaredField("sql");
        sqlField.setAccessible(true);
        String sql = boundSql.getSql();
        sqlField.set(boundSql, sql.replace(";", "") + " limit 2;");
        return invocation.proceed();
    }
}
