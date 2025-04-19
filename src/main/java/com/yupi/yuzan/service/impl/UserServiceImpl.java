package com.yupi.yuzan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuzan.mapper.UserMapper;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 葛成
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-04-19 20:27:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




