package com.sun.springbootdemo;

import com.sun.springbootdemo.utils.JwtUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/7/31 20:46
 */
public class JwtTest extends BaseJnuit5Test {

    @Test
    public void jwtTest() {
        Map<String, String> claims = new HashMap<>();
        claims.put("userName", "张三");
        claims.put("passWord", "123456");
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciwiJIUzI1NiJ9.eyJwYXNzV29yZCI6IjEyMzQ1NiIsImlzcyI6InN0YXJ0X2p3dCIsImV4cCI6MTY0MTY0MzQ5OSwidXNlck5hbWUiOiLlvKDkuIkifQ.POYr-ioYYGqCnQ_1BzZOqlcneeBesmaqt8MNkw3NIVE";
        // token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGFydF9qd3QiLCJleHAiOjE2Mjc3OTMxNjAsInVzZXJJZCI6IjEwMDAyIn0.5j60fRIJ9B0LnJcEj9E9c53nUmJR0ebzs35UUsrsY1U
        //System.out.println(JwtUtils.genJwt(claims));
        Map<String, String> stringStringMap = JwtUtils.parseToken(token);
    }

}
