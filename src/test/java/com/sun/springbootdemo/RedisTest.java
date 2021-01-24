package com.sun.springbootdemo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/11 18:06
 */
@Log4j2
public class RedisTest extends BaseJnuit5Test {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("Redis-String类型测试")
    public void stringTest() {
        String key1 = "domainList1";
        String key2 = "domainList2";
        Long add1 = redisTemplate.boundSetOps(key1).add("1", "2", "3", "4", "5");
        Long add2 = redisTemplate.boundSetOps(key2).add("3", "4", "5", "6");
        Set<Object> diff = redisTemplate.boundSetOps(key1).diff(key2);
        log.info("diff:{}", diff);

    }

    @Test
    public void test() {
        System.out.println(OffsetDateTime.now().plus(Duration.ofHours(2)));
    }


}
