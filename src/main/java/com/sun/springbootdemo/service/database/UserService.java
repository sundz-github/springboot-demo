package com.sun.springbootdemo.service.database;

import com.sun.springbootdemo.dto.UserDTO;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<UserDTO> queryAll() {
        List<User> users = userMapper.selectAll();
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().filter(Objects::nonNull).map(it -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(it, dto);
            dto.setRoleEnum(Objects.isNull(it.getRoleEnum()) ? StringUtils.EMPTY : it.getRoleEnum().getRole());
            return dto;
        }).collect(Collectors.toList());
    }
}
