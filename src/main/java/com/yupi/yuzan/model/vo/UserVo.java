package com.yupi.yuzan.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
}
