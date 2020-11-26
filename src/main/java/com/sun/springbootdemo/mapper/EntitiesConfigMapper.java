package com.sun.springbootdemo.mapper;

import com.sun.springbootdemo.service.entities.City;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/10 10:00
 */
@Repository
public interface EntitiesConfigMapper {

    /**
     * @Description: 获取Nthash密码操作
     * @Param: [paramMap]
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: Sundz
     * @Version: V1.0
     * @Date: 2020/8/10 10:16
     */
    List<Map<String, Object>> selectUserInfo(Map<String, Object> paramMap);

    City selectInfo();

    @Select("select * from cic_user_info user")
    List<Map<String, Object>> findAll();

}
