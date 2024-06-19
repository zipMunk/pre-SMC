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
@TableName("illness_medicine")
public class IllnessMedicine {

    /**
     * 主键ID
     * 该字段是疾病与药物关联表的主键，并且是自动递增的。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 疾病ID
     * 该字段存储与药物相关的疾病ID。
     */
    private Integer illnessId;

    /**
     * 药物ID
     * 该字段存储与疾病相关的药物ID。
     */
    private Integer medicineId;

    /**
     * 创建时间
     * 该字段存储记录的创建时间戳。
     */
    private Date createTime;

    /**
     * 更新时间
     * 该字段存储记录的最后更新时间戳。
     */
    private Date updateTime;

}
