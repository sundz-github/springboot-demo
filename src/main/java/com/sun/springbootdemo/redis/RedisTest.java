package com.sun.springbootdemo.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2020/12/30 16:12
 */
public class RedisTest {

    @Test
    public void test() {
        JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), "192.168.2.37");
        Jedis jedis = jedisPool.getResource();
        jedis.set("domain1", "y39eh93d3ir034utrfg945it-f0i-0");
        String domain1 = jedis.get("domain1");
        
        System.out.println(domain1);
    }

}
