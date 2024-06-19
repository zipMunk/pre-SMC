package com.SmartMed_Connect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.SmartMed_Connect.entity.Medicine;

import java.util.List;
import java.util.Map;
/**
 * 药物信息数据访问接口
 * 继承自 MyBatis-Plus 的 BaseMapper 接口，提供了基本的 CRUD 方法。
 */
@Repository
public interface MedicineDao extends BaseMapper<Medicine> {

    /**
     * 根据疾病查询药物
     *
     * @param illnessId 疾病ID
     * @return 包含药物信息的列表
     */
    List<Map<String, Object>> findMedicineList(Integer illnessId);

}
