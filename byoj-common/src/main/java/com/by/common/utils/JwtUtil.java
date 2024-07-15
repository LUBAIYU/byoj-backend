package com.by.common.utils;

import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * JWT令牌工具类
 *
 * @author lzh
 */
public class JwtUtil {

    /**
     * 创建JWT令牌
     *
     * @param payload   载荷
     * @param secretKey 签名密钥
     * @return JWT令牌
     */
    public static String createToken(Map<String, Object> payload, String secretKey) {
        // 设置JWT的签发时间
        payload.put(JWTPayload.ISSUED_AT, LocalDateTime.now());
        // 设置JWT的过期时间
        payload.put(JWTPayload.EXPIRES_AT, LocalDateTime.now().plusDays(1));
        // 设置JWT的生效时间
        payload.put(JWTPayload.NOT_BEFORE, LocalDateTime.now());
        // 创建令牌
        return JWTUtil.createToken(payload, secretKey.getBytes());
    }
}
