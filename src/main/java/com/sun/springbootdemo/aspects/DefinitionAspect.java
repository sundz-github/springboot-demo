package com.sun.springbootdemo.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/19 15:15
 */
@Aspect
@Component
@Log4j2
public class DefinitionAspect {
    /**
     * 自定义通知  括号为切入点 方法表示要执行的操作（通用功能）
     * 可以将目标方法的参数及方法执行结果传入切面方法
     * argNames表示其参数名称  声明顺序必须与方法形参声明顺序一致(JoinPoint参数必须放在前面，否则将报错)
     */
    @AfterReturning(pointcut = "execution(* com.sun.springbootdemo.config.CommconProperty.test1(..)) && args(arg)", returning = "resultValue", argNames = "joinPoint,arg,resultValue")
    public void operationAspect(JoinPoint joinPoint, String arg, String resultValue) {
        log.info("切面操作正在执行.................-->> " + arg + " " + resultValue);
        String signatureName = joinPoint.getSignature().getName();
        log.info("signatureName:{}", signatureName);
        Object args = joinPoint.getArgs();
        log.info("args:{}", args);
        String targetClassName = joinPoint.getTarget().getClass().getName();
        log.info("targetClassName:{}", targetClassName);
        String kinds = joinPoint.getKind();
        log.info("kinds:{}", kinds);
    }

    /**
     * @field: 环绕通知  -- >> ProceedingJoinPoint   统计方法耗时
     */
    @Around(value = "execution(* com.sun.springbootdemo.config.CommconProperty.test2(..)) && args(arg)", argNames = "joinPoint,arg")
    public Object operationAspect(ProceedingJoinPoint joinPoint, String arg) throws Throwable {
        log.info("环绕通知正在执行.....................");
        long currentTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        Class declaringType = signature.getDeclaringType();
        int modifiers = signature.getModifiers();
        String declaringTypeName = signature.getDeclaringTypeName();

        /**
         * 放行被拦截的目标方法并返回目标方法返回值
         */
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("执行方法耗时为:{}", (endTime - currentTime) + "毫秒");
        return result;
    }


}
