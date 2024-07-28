package com.by.common.aop;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.by.common.annotation.PreAuthorize;
import com.by.common.constant.CommonConstants;
import com.by.common.enums.ErrorCode;
import com.by.common.exception.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验切面类
 *
 * @author lzh
 */
@Component
@Aspect
public class AuthenticateAspect {

    /**
     * 权限校验
     *
     * @param joinPoint    连接点
     * @param preAuthorize 注解
     * @return Object
     */
    @Around("@annotation(preAuthorize)")
    public Object authenticate(ProceedingJoinPoint joinPoint, PreAuthorize preAuthorize) throws Throwable {
        // 获取请求对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取请求头
        String authorization = request.getHeader(CommonConstants.AUTHORIZATION);
        // 获取Token
        String token = authorization.substring(CommonConstants.TOKEN_PREFIX.length()).trim();
        // 解析Token获取用户角色
        JWT jwt = JWTUtil.parseToken(token);
        int role = Integer.parseInt(jwt.getPayload("role").toString());
        // 判断权限
        Integer userRole = preAuthorize.role().getValue();
        if (role != 0 && role != userRole) {
            throw new ServerException(ErrorCode.NO_AUTH_ERROR);
        }
        return joinPoint.proceed();
    }
}
