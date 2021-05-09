package com.sun.springbootdemo;

import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import com.sun.springbootdemo.service.database.RoleService;
import com.sun.springbootdemo.service.database.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

       /* User user = new User();
        user.setAge(29);
        user.setUserName("夏巽鹏");
        user.setPassWord("2468");
        user.setDate(new Date());
        user.setRoleEnum(RoleEnum.ADMINSTRATION);
        getMethods();*/
        /*int insert = userMapper.updateRole(9, RoleEnum.NORMAL);
        System.out.println(insert);*/
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").format(LocalDateTime.now()));
    }

    private void getMethods() {
        Map<Integer, Method> methodMap = new HashMap<>(8);
        Method[] methods = User.class.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            switch (name) {
                case "getId":
                    methodMap.put(0, method);
                    break;
                case "getUserName":
                    methodMap.put(1, method);
                    break;
                case "getPassWord":
                    methodMap.put(2, method);
                    break;
                case "getAge":
                    methodMap.put(3, method);
                    break;
                case "getRoleEnum":
                    methodMap.put(4, method);
                    break;
                case "getDate":
                    methodMap.put(5, method);
                    break;
                default:
                    break;
            }
        }
    }
}
