package com.sun.springbootdemo.entities;

import com.sun.springbootdemo.annotation.ColumnIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@EqualsAndHashCode(callSuper = true)
@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@ToString
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3493729785479686616L;


    private Long id;

    @ColumnIndex(index = 0)
    private String userName;

    @ColumnIndex(index = 2)
    private Integer age;

    @ColumnIndex(index = 1)
    private String passWord;

    private RoleEnum roleEnum;


    /* @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //设置接收日期参数时的格式
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")*/
    private Date date;


    /**
     * @Author: Sundz
     * @Description: 数据模型  -->  获取User集合对象
     * @Date 2020/6/17 11:20
     * @Param []
     * @Return java.util.Map<java.lang.String, com.sun.springbootdemo.entities.User>
     **/
    public Map<String, User> generateUserMap() {
        Map<String, User> userMap = new HashMap<>();
        Record record = new Record();
        record.setId(1L);
        record.setName("记录一");
        User user = new User();
        user.setUserName("小明");
        user.setAge(18);
        userMap.put("user", user);
        return userMap;
    }

}

