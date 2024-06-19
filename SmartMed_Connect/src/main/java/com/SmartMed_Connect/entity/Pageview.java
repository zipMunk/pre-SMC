package com.SmartMed_Connect.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * Pageview 实体类
 * 该类表示 SmartMed Connect 应用中的页面浏览量信息。
 * 它使用 MyBatis-Plus 注解进行 ORM 映射，并使用 Lombok 注解减少样板代码。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)// Lombok 注解，用于启用链式调用
@TableName("pageview")
public class Pageview implements Serializable {

    /**
     * 浏览量主键id
     * 该字段是页面浏览量表的主键。
     */
    private int id;

    /**
     * 浏览量
     * 该字段存储某页面的浏览量。
     */
    private Integer pageviews;


    /**
     * 病的id
     * 该字段存储与浏览量关联的疾病ID。
     */
    private Integer illnessId;
}
