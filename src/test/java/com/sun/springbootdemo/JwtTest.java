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
        claims.put("userId", "10002");
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGFydF9qd3QiLCJleHAiOjE2Mjc3OTMxNjAsInVzZXJJZCI6IjEwMDAyIn0.5j60fRIJ9B0LnJcEj9E9c53nUmJR0ebzs35UUsrsY1U";
        // token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdGFydF9qd3QiLCJleHAiOjE2Mjc3OTMxNjAsInVzZXJJZCI6IjEwMDAyIn0.5j60fRIJ9B0LnJcEj9E9c53nUmJR0ebzs35UUsrsY1U
        Map<String, String> stringStringMap = JwtUtils.verifyToken(token);
    }

}
