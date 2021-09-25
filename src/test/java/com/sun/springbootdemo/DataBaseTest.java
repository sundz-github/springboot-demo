package com.sun.springbootdemo;

import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.service.database.RoleService;
import com.sun.springbootdemo.service.database.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/3/30 17:13
 */
@Log4j2
public class DataBaseTest extends BaseJnuit5Test {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Resource
    private UserMapper userMapper;


    @Test
    public void userSelect() {
        userService.test(64);
        log.info("删除成功!");
    }

}
