package com.by.common.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.by.common.constant.CommonConstants;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 获取Token并解析返回用户角色
     *
     * @param request 请求
     * @return 0-管理员，1-用户
     */
    public static Integer getUserRole(HttpServletRequest request) {
        // 获取请求头
        String authorization = request.getHeader(CommonConstants.AUTHORIZATION);
        // 获取Token
        String token = authorization.substring(CommonConstants.TOKEN_PREFIX.length()).trim();
        // 解析Token获取用户角色
        JWT jwt = JWTUtil.parseToken(token);
        return Integer.parseInt(jwt.getPayload("role").toString());
    }

    /**
     * 获取Token并解析返回用户ID
     *
     * @param request 请求
     * @return 用户ID
     */
    public static Long getUserId(HttpServletRequest request) {
        // 获取请求头
        String authorization = request.getHeader(CommonConstants.AUTHORIZATION);
        // 获取Token
        String token = authorization.substring(CommonConstants.TOKEN_PREFIX.length()).trim();
        // 解析Token获取用户角色
        JWT jwt = JWTUtil.parseToken(token);
        return Long.parseLong(jwt.getPayload("userId").toString());
    }
}
