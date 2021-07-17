package com.sun.springbootdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.springbootdemo.entities.Record;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
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
        Record r1 = new Record(1, "数学");
        Record r2 = new Record(2, "语文");
        Record r3 = new Record(3, "英语");
        List<Record> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        list.add(r3);
        /*for (Record r : list) {
            redisTemplate.opsForList().leftPush("records", objectMapper.writeValueAsString(r));
        }*/
        List<Object> records = redisTemplate.opsForList().range("records", 0, -1);
        List<Record> recordList = objectMapper.readValue(records.toString(), new TypeReference<List<Record>>() {
        });
        System.out.println(recordList.get(2));
        // redisTemplate.opsForValue().set("r1", objectMapper.writeValueAsString(r1));

        /*Record record = objectMapper.readValue(String.valueOf(redisTemplate.opsForValue().get("r1")), Record.class);
        System.out.println(record);*/
        //System.out.println(r1.toString());
        //System.out.println(redisTemplate.opsForList().leftPushAll("records", list));
        /*System.out.println(redisTemplate.opsForList().range("records", 0, -1));*/
        //redisTemplate.opsForList().leftPush("subject", "英语");

    }


}
