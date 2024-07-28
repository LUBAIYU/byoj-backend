package com.by.user.controller;

import com.by.common.annotation.PreAuthorize;
import com.by.common.enums.ErrorCode;
import com.by.common.enums.RoleEnum;
import com.by.common.exception.ServerException;
import com.by.common.utils.PageBean;
import com.by.common.utils.Result;
import com.by.model.dto.UserLoginDTO;
import com.by.model.dto.UserPageDTO;
import com.by.model.dto.UserRegisterDTO;
import com.by.model.dto.UserUpdateDTO;
import com.by.model.entity.User;
import com.by.model.vo.UserVO;
import com.by.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author lzh
 */
@RestController
@RequestMapping("/")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result<Long> userRegister(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        long id = userService.register(userRegisterDTO);
        return Result.success(id);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable("id") @NotNull Long id) {
        userService.removeUserById(id);
        return Result.success(true);
    }

    @PutMapping("/update")
    @PreAuthorize(role = RoleEnum.ADMIN)
    public Result<Boolean> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUserById(userUpdateDTO);
        return Result.success(true);
    }

    @PreAuthorize(role = RoleEnum.USER)
    @GetMapping("/vo/{id}")
    public Result<UserVO> getUserVoById(@NotNull @PathVariable("id") Long id) {
        UserVO userVO = userService.getUserById(id);
        return Result.success(userVO);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @GetMapping("/{id}")
    public Result<User> getUserById(@NotNull @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        return Result.success(user);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @PostMapping("/vo/page")
    public Result<PageBean<UserVO>> pageUserVO(@NotNull @RequestBody UserPageDTO userPageDTO) {
        PageBean<UserVO> pageBean = userService.listUserVoByPage(userPageDTO);
        return Result.success(pageBean);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @PostMapping("/page")
    public Result<PageBean<User>> pageUser(@Valid @RequestBody UserPageDTO userPageDTO) {
        PageBean<User> pageBean = userService.listUserByPage(userPageDTO);
        return Result.success(pageBean);
    }
}
