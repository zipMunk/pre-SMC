package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * Illness 实体类
 * 表示 SmartMed Connect 应用中的疾病信息。
 * 使用 MyBatis-Plus 注解进行 ORM 映射，使用 Lombok 注解减少样板代码。
 */
@Data// Lombok 注解，用于生成 getter、setter、toString、equals 和 hashCode 方法
@NoArgsConstructor// Lombok 注解，用于生成无参构造函数
@AllArgsConstructor// Lombok 注解，用于生成全参构造函数
@Builder// Lombok 注解，用于实现该类的构建者模式
@TableName("illness")// MyBatis-Plus 注解，指定对应的数据库表名
public class Illness {

    /**
     * 该字段是疾病表的主键，并且是自动递增的。
     */
    @TableId(type = IdType.AUTO)// MyBatis-Plus 注解，指定主键的自增策略
    private Integer id;

    /**
     * 该字段存储疾病所属的种类 ID。
     */
    private Integer kindId;

    /**
     * 该字段存储疾病的名称。
     */
    private String illnessName;

    /**
     * 该字段存储引起疾病的原因。
     */
    private String includeReason;

    /**
     * 该字段存储疾病的主要症状。
     */
    private String illnessSymptom;

    /**
     * 该字段存储疾病的特殊症状。
     */
    private String specialSymptom;

    /**
     * 该字段存储疾病信息的创建时间戳。
     */
    private Date createTime;

    /**
     * 该字段存储疾病信息的最后更新时间戳。
     */
    private Date updateTime;

    /**
     * 疾病种类对象
     * 表示疾病所属的种类对象，在数据库表中不存在。
     * 当 MyBatis-Plus 处理实体类与数据库表之间的映射时，
     * 使用了@TableField(exist = false) 注解的字段不会被映射到数据库表中的任何列。
     * 在业务逻辑中，我们需要临时存储一些不需要持久化的数据。
     */
    @TableField(exist = false)// MyBatis-Plus 注解，指定该字段在数据库表中不存在
    private IllnessKind kind;

    /**
     * 疾病相关药物对象
     * 该字段表示与疾病相关的药物对象，在数据库表中不存在。
     */
    @TableField(exist = false)// MyBatis-Plus 注解，指定该字段在数据库表中不存在
    private IllnessMedicine illnessMedicine;
}
