package com.sun.springbootdemo.service.database;

import com.sun.springbootdemo.entities.Role;
import com.sun.springbootdemo.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p> 角色服务 </p>
 *
 * @author Sundz
 * @date 2021/5/2 20:03
 */
@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * @field 插入
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public int deleteById(Integer id) {
        if (id == 9) {
            throw new RuntimeException("删除");
        }
        return roleMapper.deleteById(id);
    }
}
