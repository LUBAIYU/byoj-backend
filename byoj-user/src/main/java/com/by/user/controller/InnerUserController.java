package com.by.user.controller;

import com.by.model.entity.User;
import com.by.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 提供服务内部调用的接口
 *
 * @author lzh
 */
@RestController
@RequestMapping("/inner")
@Validated
public class InnerUserController {

    @Resource
    private UserService userService;

    @GetMapping("/get")
    public User getById(@RequestParam("id") @NotNull Long id) {
        return userService.getById(id);
    }
}
