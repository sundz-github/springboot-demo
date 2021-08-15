package com.sun.springbootdemo.retry;

import com.sun.springbootdemo.entities.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * <p> spring-retry重试机制 </p>
 * 1、Retryable方法只有直接通过代理对象调用时才会生效，通过其他方法间接调用不生效（所有基于AOP的注解都一样）；
 * 2、基于JDK动态代理时，只代理接口中的方法；
 * 3、基于CGLIB动态代理时，可以代理目标类所有可继承方法 ，SpringBoot默认采用CGLIB代理，所以重试方法应该定义在接口
 *
 * @author Sundz
 * @date 2021/1/11 20:38
 */
@Service
@Log4j2
public class RetryServiceImpl implements RetryService {

    private int count;

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 3000, maxDelay = 10000, multiplier = 1))
    @Override
    public String apply(String param) {
        count++;
        log.info("第【{}】次执行apply方法了!", count);
        if (param.equals("1")) {
            throw new RuntimeException("重试异常!");
        }
        return param;
    }

    /**
     * 设置重试失败后的回调机制
     *
     * @param e 异常
     * @return {@link Result<String>}  该方法的方法返回值应该与重试方法一致
     */
    @Override
    public String recover(Exception e, String param) {
        log.info("recover的熔断措施执行了！");
        return "recover的熔断措施执行了";
    }
}
