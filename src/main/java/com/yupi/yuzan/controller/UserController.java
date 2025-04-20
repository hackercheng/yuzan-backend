package com.yupi.yuzan.controller;

import com.yupi.yuzan.common.BaseResponse;
import com.yupi.yuzan.common.ErrorCode;
import com.yupi.yuzan.common.Result;
import com.yupi.yuzan.exception.BusinessException;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.model.dto.UserDto;
import com.yupi.yuzan.model.dto.user.UserRegisterRequest;
import com.yupi.yuzan.model.vo.LoginUserVo;
import com.yupi.yuzan.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (ObjectUtils.isEmpty(userRegisterRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为null");
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        Long register = userService.register(userAccount, password, checkPassword);
        return Result.success(register);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVo> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(userDto)) {
            log.info("用户登录接口/user/login"+"请求参数为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        try {
            String userAccount = userDto.getUserAccount();
            String userPassword = userDto.getUserPassword();
            // 用户登录
            LoginUserVo userVo = userService.userLogin(userAccount, userPassword, request);
            return Result.success(userVo);
        } catch (BusinessException e) {
            log.error("用户登录失败，用户不存在或账户名密码错误");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("用户登录接口/user/login"+"异常",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getLoginUser")
    public BaseResponse<LoginUserVo> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return Result.success(userService.getLoginUserVo(loginUser));
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        boolean logout = userService.userLogout(request);
        return Result.success(logout);
    }
}
