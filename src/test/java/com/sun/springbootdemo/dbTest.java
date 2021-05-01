package com.sun.springbootdemo;

import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.RoleMapper;
import com.sun.springbootdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/3/30 17:13
 */
public class dbTest extends BaseJnuit5Test {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;


    @Test
    public void userSelect() {
        /*List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);*/
        User user = userMapper.selectOne("李钊");
        System.out.println("user->>" + user);
        //Role role = roleMapper.selectOne(1L);
    }
}
