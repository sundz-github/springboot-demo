package com.sun.springbootdemo.mapper;

import com.sun.springbootdemo.entities.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/10 10:00
 */
@Repository
public interface UserMapper {

    List<User> selectAll();

    User selectOne(@Param("userName") String userName);


    //void insert(User user);


}
