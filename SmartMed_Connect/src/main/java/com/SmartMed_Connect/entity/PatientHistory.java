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
 * 表示病人病史的实体类。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("patient_history")
public class PatientHistory {
    /**
     * 主键ID
     * 该字段是病史表的主键，并且是自动递增的。
     */
    @TableId(type=IdType.AUTO)
    private Integer id;

    /**
     * 表示与此病史关联的用户的ID。
     */
    private Integer UserId;

    /**
     * 表示病人第一次询问的问题。
     */
    private String firstQuery;

    /**
     * 表示用户的年龄。
     */
    private Integer userAge;

    /**
     * 表示用户的性别。
     */
    private String userSex;

    /**
     * 表示用户的身高和体重。
     */
    private String heightAndWeight;

    /**
     * 表示病人所经历的症状。
     */
    private String symptoms;

    /**
     * 相关发作的详细信息，如发作时间、持续时间、发作频率等。
     */
    private String episodeDetails;

    /**
     * 表示生活方式因素，包括饮食、运动和睡眠等。
     */
    private String lifestyleFactors;

    /**
     * 表示用户的过往病史。
     */
    private String medicalHistory;

    /**
     * 表示病人对哪些药物过敏。
     */
    private String allergicDrugs;

    /**
     * 表示病人目前正在使用的药物。
     */
    private String usingDrugs;

    /**
     * 表示记录创建的时间。
     */
    private Date createTime;

    /**
     * 表示病人的诊断结果。
     */
    private String diagnosticResult;

    @Override
    public String toString() {
        return "病史{      " +
                "病史Id：'" + id +'\'' +";"+
                "    病人的询问的问题是：'" + firstQuery + '\'' +";"+
                "    用户年龄：'" + userAge +'\'' +";"+
                "    用户性别：'" + userSex + '\'' +";"+
                "    用户身高和体重：'" + heightAndWeight + '\'' +";"+
                "    症状：'" + symptoms + '\'' +";"+
                "    病情发作相关细节，如发作时间，持续时间，发作频率：'" + episodeDetails + '\'' +";"+
                "    生活方式因素，包括饮食、运动、睡眠等：'" + lifestyleFactors + '\'' +";"+
                "    病史：'" + medicalHistory + '\'' +";"+
                "    过敏药物：'" + allergicDrugs + '\'' +";"+
                "    正在服用的药物：'" + usingDrugs + '\'' +";"+
                "    记录时间：'" + createTime +";"+
                "    诊断结果：'" + diagnosticResult + '\'' +";"+
                "       }";
    }
}
