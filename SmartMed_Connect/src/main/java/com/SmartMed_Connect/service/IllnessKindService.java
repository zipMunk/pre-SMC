package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.IllnessKindDao;
import com.SmartMed_Connect.entity.IllnessKind;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class IllnessKindService extends BaseService<IllnessKind> {

    // 自动注入 IllnessKindDao 对象
    @Autowired
    protected IllnessKindDao illnessKindDao;

    // 查询方法，根据传入的 IllnessKind 对象的非空字段生成查询条件
    @Override
    public List<IllnessKind> query(IllnessKind o) {
        QueryWrapper<IllnessKind> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);// 将对象转换为 Map
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;// 跳过空值字段
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return illnessKindDao.selectList(wrapper);// 执行查询并返回结果列表
    }

    // 查询所有记录的方法
    @Override
    public List<IllnessKind> all() {
        return query(null);// 查询条件为 null，则返回所有记录
    }

    // 保存方法，根据是否有 ID 来判断是插入还是更新操作
    @Override
    public IllnessKind save(IllnessKind o) {
        if (Assert.isEmpty(o.getId())) {
            illnessKindDao.insert(o);// ID 为空则插入新记录
        } else {
            illnessKindDao.updateById(o); // ID 不为空则更新记录
        }
        return illnessKindDao.selectById(o.getId()); // 返回保存后的记录
    }

    // 根据 ID 获取单条记录的方法
    @Override
    public IllnessKind get(Serializable id) {
        return illnessKindDao.selectById(id);
    }

    // 根据 ID 删除记录的方法
    @Override
    public int delete(Serializable id) {
        return illnessKindDao.deleteById(id);// 根据 ID 删除记录并返回影响的行数
    }

    // 获取所有 IllnessKind 列表的方法
    public List<IllnessKind> findList() {
        return illnessKindDao.selectList(new QueryWrapper<IllnessKind>());// 执行查询并返回结果列表
    }
}