package com.yupi.yuzan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuzan.common.ErrorCode;
import com.yupi.yuzan.constant.UserConstant;
import com.yupi.yuzan.exception.BusinessException;
import com.yupi.yuzan.mapper.UserMapper;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.model.vo.LoginUserVo;
import com.yupi.yuzan.model.vo.UserVo;
import com.yupi.yuzan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author 葛成
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-04-19 20:27:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

//    private static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    @Override
    public Long register(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名错误");
        }

        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }

        if (checkPassword.length() < 8 || !checkPassword.equals(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误");
        }

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount,userAccount);
        User user1 = baseMapper.selectOne(lqw);
        if (ObjectUtils.isNotEmpty(user1)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名已存在");
        }

        String encryPassword = getEncryPassword(userPassword);
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryPassword);
        int insert = baseMapper.insert(user);
        if (insert < 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"注册失败");
        }

        return user.getId();
    }

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isEmpty(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (StringUtils.isEmpty(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名错误");
        }

        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }

        String encryPassword = getEncryPassword(userPassword);

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount,userAccount);
        lqw.eq(User::getUserPassword,encryPassword);
        User user = this.baseMapper.selectOne(lqw);
        if (ObjectUtils.isEmpty(user)) {
            log.error("用户登录失败，用户不存在或账户名密码错误");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户登录失败，用户不存在或账户名密码错误");
        }

        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);
        return this.getLoginUserVo(user);
    }

    @Override
    public String getEncryPassword(String userPassword) {
        String salt = "yupi";
        return DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
    }

    @Override
    public LoginUserVo getLoginUserVo(User user) {
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        LoginUserVo userVo = new LoginUserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (ObjectUtils.isEmpty(attribute)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"当前未登录");
        }
        User currentUser = (User) attribute;
        currentUser = this.getById(currentUser.getId());
        if (ObjectUtils.isEmpty(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"当前未登录");
        }
        return currentUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (ObjectUtils.isEmpty(attribute)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"当前未登录");
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }
}




