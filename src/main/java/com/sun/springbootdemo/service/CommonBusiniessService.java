package com.sun.springbootdemo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: CommonBusiniessService
 * @ClassName: CommonBusiniessService
 * @Author: Sundz
 * @Date: 2020/6/17 10:51
 * @Version: V1.0
 **/
@Service
@Log4j2
//@Transactional(rollbackFor = Exception.class, transactionManager = "dataSourceTransactionManagerMaster")
public class CommonBusiniessService {

    private static final long serialVersionUID = -6408834079836580445L;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    //@Qualifier("caffeine")
    private CacheManager caffeine;

    @Cacheable(value = "paraview", key = "#id", condition = "(#id%2) !=0"/*, cacheManager = "caffeine"*/)
    //@CacheEvict(value = "paraview", allEntries = true)  //清除所有的缓存
    public int testCache(int id) {
        log.info("<------------------------进入缓存了------------------------>");
        return id + 1;
    }

}
