package com.by.common.annotation;

import com.by.common.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * @author lzh
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface PreAuthorize {

    RoleEnum role() default RoleEnum.USER;
}
