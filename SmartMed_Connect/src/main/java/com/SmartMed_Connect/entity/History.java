package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * History 实体类
 * 该类表示 SmartMed Connect 应用中的浏览历史记录。
 * 它使用 MyBatis-Plus 注解进行 ORM 映射，并使用 Lombok 注解减少样板代码。
 */
@Data// Lombok 注解，用于生成 getter、setter、toString、equals 和 hashCode 方法
@NoArgsConstructor// Lombok 注解，用于生成无参构造函数
@AllArgsConstructor// Lombok 注解，用于生成全参构造函数
@Builder// Lombok 注解，用于实现该类的构建者模式
@Accessors(chain = true)// Lombok 注解，用于实现链式调用
@TableName("history")// MyBatis-Plus 注解，指定对应的数据库表名
public class History {

    /**
     * 主键ID
     * 该字段是浏览历史表的主键，并且是自动递增的。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 浏览历史关联用户id
     * 该字段存储与浏览历史关联的用户ID。
     */
    private Integer userId;

    /**
     * 浏览历史类型
     * 该字段存储浏览历史的操作类型。
     */
    private Integer operateType;

    /**
     * 浏览历史关键字
     * 该字段存储与浏览历史相关的关键字。
     */
    private String keyword;

    /**
     * 浏览时间
     * 该字段存储浏览的时间戳。
     */
    private Date createTime;

    /**
     * 更新时间
     * 该字段存储记录的最后更新时间戳。
     */
    private Date updateTime;

}
