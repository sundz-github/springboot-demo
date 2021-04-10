package com.sun.springbootdemo.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Objects;

/**
 * @description: 注意序列化器不同会导致取值无法取出，必须保证set时的序列化器与get时的序列化相同才可取出值
 * @author: Sundz
 * @version: V1.0
 * @date: 2020/9/22 18:05
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.cache.nodes:}")
    private String nodes;
    @Value("${spring.redis.cache.host:}")
    private String host;
    @Value("${spring.redis.cache.password:}")
    private String password;
    @Value("${spring.redis.cache.maxIdle:}")
    private Integer maxIdle;
    @Value("${spring.redis.cache.minIdle:}")
    private Integer minIdle;
    @Value("${spring.redis.cache.maxTotal:}")
    private Integer maxTotal;
    @Value("${spring.redis.cache.maxWaitMillis:}")
    private Long maxWaitMillis;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
        poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
        poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis);
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();
        // 单机redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(6379);
        // 设置分区
        redisConfig.setDatabase(0);
        if (password != null && !"".equals(password)) {
            redisConfig.setPassword(password);
        }

        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();

        // 集群redis  -->> 需要开启集群功能，默认没有开启cluster-enabled yes，
       /* RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodeses = new HashSet<>();
        String[] hostses = nodes.split("-");
        for (String h : hostses) {
            h = h.replaceAll("\\s", "").replaceAll("\n", "");
            if (!"".equals(h)) {
                String host = h.split(":")[0];
                int port = Integer.parseInt(h.split(":")[1]);
                nodeses.add(new RedisNode(host, port));
            }
        }
        redisConfig.setClusterNodes(nodeses);
        // 跨集群执行命令时要遵循的最大重定向数量
        redisConfig.setMaxRedirects(3);
        redisConfig.setPassword(password);*/

        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }

    /**
     * 健采用StringRedisSerializer序列化器，值采用Jackson2JsonRedisSerializer序列化器
     *
     * @param redisConnectionFactory
     * @return {@link RedisTemplate< String, Object>}
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * RedisCacheManager,且设置过期时间,注：缓存管理器只会生效一个
     *
     * @param redisTemplate
     * @return {@link CacheManager}
     */
    @Bean("redis")
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        //全局redis缓存过期时间
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(objectMapper);
        //设置缓存时间 1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1));
        //设置缓存key的序列化
        redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        //设置缓存value的序列化
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer));
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


}
