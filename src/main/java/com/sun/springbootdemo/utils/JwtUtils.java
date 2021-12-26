package com.sun.springbootdemo.utils;

import cn.hutool.core.map.MapUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p> JWT工具类 jwt:header+payload+signature </p>
 *
 * @author Sundz
 * @date 2021/7/31 18:44
 */
@Log4j2
public final class JwtUtils {

    /**
     * @field 密钥
     */
    private static final String SECRET_KEY = "sundengzhi123456";

    /**
     * @field 发行人
     */
    private static final String ISSUER = "start_jwt";

    /**
     * @field 生成token
     */
    public static String genJwt(Map<String, String> claims) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addMinutes(new Date(), 30));
        // 添加payload
        claims.forEach(builder::withClaim);
        String token = builder.sign(algorithm);
        if (MapUtil.isNotEmpty(claims)) {
            String userId = claims.get("userId");
            if (StringUtils.isNotBlank(userId)) {
                RedisTemplate<String, Object> redisTemplate = SpringBeanUtils.getBean("redisTemplate", RedisTemplate.class);
                redisTemplate.opsForValue().set(userId, token);
            }
        }
        return token;
    }

    /**
     * @field 校验token
     */
    public static Map<String, String> verifyToken(String token) {
        Map<String, String> result = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier build = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT verify = null;
        try {
            verify = build.verify(token);
        } catch (TokenExpiredException e) {
            log.error(e.getMessage(), e);
        }
        if (Objects.nonNull(verify)) {
            Map<String, Claim> claims = verify.getClaims();
            claims.forEach((x, y) -> result.put(x, y.asString()));
        }
        return result;
    }
}
