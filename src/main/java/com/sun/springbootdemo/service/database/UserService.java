package com.sun.springbootdemo.service.database;

import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p> 用户服务 </p>
 *
 * @author Sundz
 * @date 2021/5/2 20:03
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;


    public User update(Integer id, String name) {
        userMapper.update(id, name);
        return userMapper.selectOne(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateUser() {
        userMapper.update(1, "李钊");
        try {
            roleService.deleteById(9);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
