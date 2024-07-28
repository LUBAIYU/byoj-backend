package com.by.gateway.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.by.common.constant.CommonConstants;
import com.by.common.utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/**
 * @author lzh
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter {

    @Value("${jwt.encrypt.secretKey}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        // 判断当前操作是否是登录注册
        String path = request.getURI().getPath();
        for (String item : CommonConstants.PATH_WHITE_LIST) {
            // 如果路径包含白名单，直接放行
            if (path.contains(item)) {
                return chain.filter(exchange);
            }
        }

        // 获取请求头中的Authorization
        String authorization = headers.getFirst(CommonConstants.AUTHORIZATION);
        String message;

        // 校验格式是否正确
        if (authorization == null || !authorization.startsWith(CommonConstants.TOKEN_PREFIX)) {
            message = "Invalid or missing authorization header";
            log.error(message);
            return denyRequest(response, HttpStatus.UNAUTHORIZED, message);
        }

        // 获取Token
        String token = authorization.substring(CommonConstants.TOKEN_PREFIX.length()).trim();
        if (token.isEmpty()) {
            message = "Token is empty";
            log.error(message);
            return denyRequest(response, HttpStatus.FORBIDDEN, message);
        }

        // 验证Token，判断Token是否有效
        try {
            // 判断Token是否过期以及是否正确
            JWT jwt = JWTUtil.parseToken(token);
            long expireTimeNum = Long.parseLong(jwt.getPayload(JWTPayload.EXPIRES_AT).toString());

            // 将时间戳转换为LocalDateTime
            Instant instant = Instant.ofEpochSecond(expireTimeNum);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            LocalDateTime expireTime = zonedDateTime.toLocalDateTime();

            if (LocalDateTime.now().isAfter(expireTime) || !JWTUtil.verify(token, secretKey.getBytes())) {
                message = "Token is invalid";
                log.error(message);
                return denyRequest(response, HttpStatus.FORBIDDEN, message);
            }
        } catch (JWTException e) {
            message = "Failed to parse the token";
            log.error(message);
            return denyRequest(response, HttpStatus.FORBIDDEN, message);
        }
        return chain.filter(exchange);
    }

    /**
     * 拒绝请求
     *
     * @param response 响应体
     * @param status   响应状态
     * @param message  响应信息
     * @return Mono
     */
    public Mono<Void> denyRequest(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        // 设置响应头为JSON格式
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF8");
        // 返回错误信息到客户端
        byte[] bytes;
        try {
            bytes = new ObjectMapper().writeValueAsBytes(Result.error(status.value(), message));
        } catch (JsonProcessingException e) {
            log.error("json process exception ...");
            return response.setComplete();
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }
}
