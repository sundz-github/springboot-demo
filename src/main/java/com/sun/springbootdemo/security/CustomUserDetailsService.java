package com.sun.springbootdemo.security;

import com.sun.springbootdemo.entities.RoleEnum;
import com.sun.springbootdemo.entities.User;
import com.sun.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> 从数据库获取权限 </p>
 *
 * @author Sundz
 * @date 2021/4/11 21:04
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PRE_AUTHENTICATE_ROLE = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(username);
        RoleEnum role = user.getRoleEnum();
        int order = role.getOrder();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String roleStr;
        if (order == 0) {
            roleStr = PRE_AUTHENTICATE_ROLE + "ADMIN";
            // 管理员授权普通用户的操作
            authorities.add(new SimpleGrantedAuthority(PRE_AUTHENTICATE_ROLE + "NORMAL"));
        } else {
            roleStr = PRE_AUTHENTICATE_ROLE + "NORMAL";
        }
        authorities.add(new SimpleGrantedAuthority(roleStr));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(), passwordEncoder.encode(user.getPassWord()), authorities);
        return userDetails;
    }


}
