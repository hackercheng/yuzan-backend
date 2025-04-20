package com.yupi.yuzan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.model.vo.LoginUserVo;
import com.yupi.yuzan.model.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author 葛成
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-04-19 20:27:40
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    public Long register(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     */
    public LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 密码加密
     */
    public String getEncryPassword(String content);

    /**
     * 登录信息脱敏
     */
    public LoginUserVo getLoginUserVo(User user);

    /**
     * 获取当前登录用户
     */
    public User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);
}
