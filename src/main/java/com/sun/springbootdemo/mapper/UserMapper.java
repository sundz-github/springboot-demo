package com.sun.springbootdemo.mapper;

import com.sun.springbootdemo.entities.RoleEnum;
import com.sun.springbootdemo.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Sundz
 * @Version: V1.0
 * @Date: 2020/8/10 10:00
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAll();

    User selectOne(@Param("userName") String userName);

    int update(Integer id, String name);

    int updateRole(Integer id, RoleEnum roleEnum);

    int deleteById(@Param("id") Integer id);

    int insertBatch(@Param("list") List<User> list);

    List<Map<String, Integer>> selectUserNum();

    List<User> getUserInfo();

}
