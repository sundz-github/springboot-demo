package com.sun.springbootdemo.service;

import com.sun.springbootdemo.service.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> 公用服务类 </p>
 *
 * @author Sundz
 * @date 2021/10/16 20:31
 */
@Service
public class CommonService {

    @Autowired
    private UserService userService;


}
