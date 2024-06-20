package com.SmartMed_Connect.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//病史实体类
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

    private Integer UserId;//用户Id

    private String firstQuery;//病人第一次询问

    private Integer userAge;//用户年龄

    private String userSex;//用户性别

    private String heightAndWeight;//身高、体重

    private String symptoms;//症状

    private String episodeDetails;//病情发作相关细节，如发作时间，持续时间，发作频率

    private String lifestyleFactors;//表示生活方式因素，包括饮食、运动、睡眠等

    private String medicalHistory;//病史

    private String allergicDrugs;//过敏药物

    private String usingDrugs;//正在使用的药物

    private Date createTime;//记录时间

    private String diagnosticResult;//诊断结果

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
