package com.by.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 通用常量
 *
 * @author lzh
 */
public interface CommonConstants {

    /**
     * header
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token前缀
     */
    String TOKEN_PREFIX = "Bearer";

    /**
     * 白名单路径
     */
    List<String> PATH_WHITE_LIST = Arrays.asList("/v2/api-docs", "/user/login", "/user/register");
}
