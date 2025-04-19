package com.yupi.yuzan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.yuzan.common.BaseResponse;
import com.yupi.yuzan.common.ErrorCode;
import com.yupi.yuzan.common.Result;
import com.yupi.yuzan.exception.BusinessException;
import com.yupi.yuzan.exception.ThrowUtils;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.model.dto.UserDto;
import com.yupi.yuzan.model.vo.UserVo;
import com.yupi.yuzan.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<UserVo> login(@RequestBody UserDto userDto) {
        if (ObjectUtils.isEmpty(userDto)) {
            log.info("用户登录接口/user/login"+"请求参数为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        try {
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(StringUtils.isEmpty(userDto.getUserAccount()),User::getUserAccount,userDto.getUserAccount());
            lqw.eq(StringUtils.isEmpty(userDto.getUserPassword()),User::getUserPassword,userDto.getUserPassword());
            User user = userService.getOne(lqw);

            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);


            return Result.success(userVo);
        } catch (BeansException e) {
            log.error("用户登录接口/user/login"+"异常",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
