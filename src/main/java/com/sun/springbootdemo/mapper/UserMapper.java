package com.sun.springbootdemo.mapper;

import com.sun.springbootdemo.service.entities.User;
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

    //void insert(User user);


}
