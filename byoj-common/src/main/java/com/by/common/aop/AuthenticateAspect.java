package com.by.common.aop;

import com.by.common.annotation.PreAuthorize;
import com.by.common.enums.ErrorCode;
import com.by.common.exception.ServerException;
import com.by.common.utils.JwtUtil;
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
        // 获取用户角色
        int role = JwtUtil.getUserRole(request);
        // 判断权限
        Integer userRole = preAuthorize.role().getValue();
        if (role != 0 && role != userRole) {
            throw new ServerException(ErrorCode.NO_AUTH_ERROR);
        }
        return joinPoint.proceed();
    }
}
