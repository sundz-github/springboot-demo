package com.sun.springbootdemo.service.entities;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: User
 * @ClassName: User
 * @Author: Sundz
 * @Date: 2020/6/17 10:51
 * @Version: V1.0
 **/
@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class User implements Serializable {

    private static final long serialVersionUID = -3493729785479686616L;

    private long id;

    private String username;

    private int age;

    private Date date;


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(User.class).add("username", username).add("age", age).toString();
    }


    /**
     * @Author: Sundz
     * @Description: 数据模型  -->  获取User集合对象
     * @Date 2020/6/17 11:20
     * @Param []
     * @Return java.util.Map<java.lang.String, com.sun.springbootdemo.service.entities.User>
     **/
    public Map<String, User> generateUserMap() {
        Map<String, User> userMap = new HashMap<>();
        Record record = new Record();
        record.setId(1L);
        record.setName("记录一");
        User user = new User();
        user.setUsername("小明");
        user.setAge(18);
        userMap.put("user", user);
        return userMap;
    }


}
