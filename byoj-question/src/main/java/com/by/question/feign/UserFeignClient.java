package com.by.question.feign;

import com.by.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author lzh
 */
@FeignClient(name = "byoj-user", path = "/oj/user/inner")
public interface UserFeignClient {

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/get")
    User getById(@RequestParam("id") @NotNull Long id);
}
