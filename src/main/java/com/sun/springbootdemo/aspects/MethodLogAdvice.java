package com.sun.springbootdemo.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.springbootdemo.annotation.RequestLog;
import com.sun.springbootdemo.entities.mongo.LogMongo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p> 方法日志请求 </p>
 *
 * @author Sundz
 * @date 2021/7/25 18:28
 */
@Component
@Aspect
public class MethodLogAdvice {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @field
     */
    @AfterReturning(pointcut = "@annotation(com.sun.springbootdemo.annotation.RequestLog)")
    public void requestlog(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        int length = args.length;
        if (length > 0) {
            String content = objectMapper.writeValueAsString(args[0]);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RequestLog annotation = AnnotationUtils.findAnnotation(method, RequestLog.class);
            if (Objects.nonNull(annotation)) {
                int type = annotation.type();
                String remark = annotation.remark();
                LogMongo logMongo = new LogMongo();
                logMongo.setContentJson(content);
                logMongo.setRemark(remark);
                logMongo.setType(type);
                mongoTemplate.save(logMongo);
            }
        }
    }
}
