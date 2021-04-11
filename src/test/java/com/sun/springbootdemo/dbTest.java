package com.sun.springbootdemo;

import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        /*List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);*/
        User user = userMapper.selectOne("李钊");
        System.out.println("user->>" + user);
    }
}
