package com.yupi.yuzan.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;

    /**
     * 微信开放平台id
     */
    private String unionid;

    /**
     * 公众号openId
     */
    private String mpopenid;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String useravatar;

    /**
     * 用户简介
     */
    private String userprofile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userrole;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    /**
     * 会员过期时间
     */
    private Date vipexpiretime;

    /**
     * 会员兑换码
     */
    private String vipcode;

    /**
     * 会员编号
     */
    private Long vipnumber;

    /**
     * 分享编码
     */
    private String sharecode;

    /**
     * 邀请用户id
     */
    private Long inviteuser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}