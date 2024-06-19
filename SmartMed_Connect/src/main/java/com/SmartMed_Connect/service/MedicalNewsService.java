package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.MedicalNewsDao;
import com.SmartMed_Connect.entity.MedicalNews;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class MedicalNewsService extends BaseService<MedicalNews> {

    @Autowired
    protected MedicalNewsDao medicalNewsDao;

    /**
     * 根据条件查询 MedicalNews 对象列表
     *
     * @param o 查询条件封装的 MedicalNews 对象
     * @return 符合条件的 MedicalNews 对象列表
     */
    @Override
    public List<MedicalNews> query(MedicalNews o) {
        QueryWrapper<MedicalNews> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            // 将对象转换为 Map 形式
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                // 跳过空值字段
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                // 使用下划线形式的字段名查询
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return medicalNewsDao.selectList(wrapper);
    }

    /**
     * 查询所有的 MedicalNews 对象
     *
     * @return 所有的 MedicalNews 对象列表
     */
    @Override
    public List<MedicalNews> all() {
        return query(null);
    }

    /**
     * 保存或更新一个 MedicalNews 对象
     *
     * @param o 要保存的 MedicalNews 对象
     * @return 保存后的 MedicalNews 对象
     */
    @Override
    public MedicalNews save(MedicalNews o) {
        // 如果 ID 为空，则插入新的对象
        if (Assert.isEmpty(o.getId())) {
            medicalNewsDao.insert(o);
        } else {
            // 如果 ID 不为空，则更新已有对象
            medicalNewsDao.updateById(o);
        }
        return medicalNewsDao.selectById(o.getId());
    }

    /**
     * 根据 ID 获取一个 MedicalNews 对象
     *
     * @param id 对象的主键 ID
     * @return 获取到的 MedicalNews 对象
     */
    @Override
    public MedicalNews get(Serializable id) {
        return medicalNewsDao.selectById(id);
    }

    /**
     * 根据 ID 删除一个 MedicalNews 对象
     *
     * @param id 要删除的对象的主键 ID
     * @return 删除操作影响的行数（一般为1表示成功删除，0表示未找到）
     */
    @Override
    public int delete(Serializable id) {
        return medicalNewsDao.deleteById(id);
    }
}