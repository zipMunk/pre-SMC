package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * IllnessKind 实体类
 * 该类表示 SmartMed Connect 应用中的疾病种类信息。
 * 它使用 MyBatis-Plus 注解进行 ORM 映射，并使用 Lombok 注解减少样板代码。
 */
@Data// Lombok 注解，用于生成 getter、setter、toString、equals 和 hashCode 方法
@NoArgsConstructor// Lombok 注解，用于生成无参构造函数
@AllArgsConstructor// Lombok 注解，用于生成全参构造函数
@Builder// Lombok 注解，用于实现该类的构建者模式
@TableName("illness_kind")// MyBatis-Plus 注解，指定对应的数据库表名
public class IllnessKind {

    /**
     * 主键ID
     */
    private int id;

    /**
     * 疾病种类名字
     */
    private String name;

    /**
     * 疾病描述
     */
    private String info;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
