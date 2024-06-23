package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User {

    /**
     * 主键ID
     * 用户表的主键，并且是自动递增的。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     * 存储用户的账号名。
     */
    private String userAccount;

    /**
     * 用户真实名字
     * 存储用户的真实姓名。
     */
    private String userName;

    /**
     * 用户密码
     * 存储用户的登录密码。
     */
    private String userPwd;

    /**
     * 用户年龄
     * 存储用户的年龄。
     */
    private Integer userAge;

    /**
     * 用户性别
     * 存储用户的性别。
     */
    private String userSex;

    /**
     * 用户邮箱
     * 存储用户的电子邮箱地址。
     */
    private String userEmail;

    /**
     * 用户电话
     * 存储用户的电话号码。
     */
    private String userTel;

    /**
     * 角色状态，1代表管理员，0普通用户
     * 存储用户的角色状态，用于区分管理员和普通用户。
     */
    private Integer roleStatus;

    /**
     * 图片的地址
     * 存储用户的头像或其他图片的存储路径。
     */
    private String imgPath;

    /**
     * 创建时间
     * 存储用户账号的创建时间戳。
     */
    private Date createTime;

    /**
     * 更新时间
     * 存储用户信息的最后更新时间戳。
     */
    private Date updateTime;

}
