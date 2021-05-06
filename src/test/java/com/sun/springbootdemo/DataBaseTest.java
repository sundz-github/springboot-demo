package com.sun.springbootdemo;

import com.sun.springbootdemo.entities.RoleEnum;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.service.database.RoleService;
import com.sun.springbootdemo.service.database.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/3/30 17:13
 */
public class DataBaseTest extends BaseJnuit5Test {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Resource
    private UserMapper userMapper;


    @Test
    public void userSelect() {
        /*Role role = new Role();
        role.setName("战士");
        role.setGrade(4);
        roleService.insertRole(role);*/
        //userService.update(5, "洪德建");
        /*User user = userMapper.selectOne("李钊");
        RoleEnum roleEnum = user.getRoleEnum();
        System.out.println(roleEnum.getRole());*/

        User user = new User();
        user.setAge(29);
        user.setUserName("夏巽鹏");
        user.setPassWord("2468");
        user.setDate(new Date());
        user.setRoleEnum(RoleEnum.ADMINSTRATION);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
}
