package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * Feedback 实体类
 * 该类表示 SmartMed Connect 应用中的反馈记录。
 * 它使用 MyBatis-Plus 注解进行 ORM 映射，并使用 Lombok 注解减少样板代码。
 */
@Data// Lombok 注解，用于生成 getter、setter、toString、equals 和 hashCode 方法
@NoArgsConstructor// Lombok 注解，用于生成无参构造函数
@AllArgsConstructor// Lombok 注解，用于生成全参构造函数
@Builder// Lombok 注解，用于实现该类的构建者模式
@TableName("feedback")// MyBatis-Plus 注解，指定对应的数据库表名
public class Feedback {

    /**
     * 主键ID
     * 该字段是反馈表的主键，并且是自动递增的。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 反馈用户
     * 该字段存储提供反馈的用户的名字。
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 反馈标题
     */
    private String title;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
