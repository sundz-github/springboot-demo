package com.sun.springbootdemo.mapper;

import com.sun.springbootdemo.entities.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/4/18 19:58
 */
public interface RoleMapper {

    Role selectOne(@Param("id") Long id);

    List<Role> selectAll(@Param("id") Long id);

    int insert(@Param("role") Role role);

    int deleteById(@Param("id") Integer id);
}
