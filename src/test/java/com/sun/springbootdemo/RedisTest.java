package com.sun.springbootdemo;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

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
    public void test() throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("1", "张三");
        hashMap.put("2", null);
        hashMap.put("3", 3);
        hashMap.put("4", "张三");
        hashMap.put("5", 3);
        hashMap.put("6", '5');
        RedisTest test = new RedisTest();
        System.out.println(test.countSameValue(hashMap));
        /**
         * @field 统计Map中Value值重复的次数
         */

    }

    public Map<Object, Integer> countSameValue(final Map<String, Object> paramMap) {
        Map<Object, Integer> result = new HashMap<>();
        if (CollUtil.isEmpty(paramMap)) {
            return Collections.emptyMap();
        }
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object value = entry.getValue();
            if (Objects.isNull(value)) {
                result.put(value, 1);
                continue;
            }
            int count = 0;
            for (Map.Entry<String, Object> internalEntry : paramMap.entrySet()) {
                Object entryValue = internalEntry.getValue();
                if (Objects.equals(value, entryValue)) {
                    count++;
                }
            }
            result.put(value, count);
        }
        return result;
    }

    @Test
    public void init() {
        for (int i = 0; i < 5; i++) {
            redisTemplate.boundZSetOps("delayQueue").add("日历任务index:" + i, DateUtils.addSeconds(new Date(), 15 * i).getTime());
        }
    }

    @Test
    public void delayQueueTes() throws Exception {

        for (int i = 0; i < 5; i++) {
            redisTemplate.boundZSetOps("delayQueue").add("日历任务index:" + i, DateUtils.addSeconds(new Date(), 15 * i).getTime());
        }

        new Thread(() -> {
            while (true) {
                Set<Object> delayQueue = redisTemplate.boundZSetOps("delayQueue").range(0, 0);
                if (CollectionUtils.isEmpty(delayQueue)) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
                for (Object task : delayQueue) {
                    long score = BigDecimal.valueOf(redisTemplate.boundZSetOps("delayQueue").score(task)).longValue();
                    if (new Date().getTime() >= score) {
                        System.out.println(task);
                        redisTemplate.boundZSetOps("delayQueue").remove(task);
                    }
                }
            }
        }).start();
        TimeUnit.MINUTES.sleep(5);
    }


}
