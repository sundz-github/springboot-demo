package com.sun.springbootdemo;

import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.service.entities.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/3/30 17:13
 */
public class dbTest extends BaseJnuit5Test {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionFactory sessionFactory;


    @Test
    public void userSelect() {

        List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);
    }
}
