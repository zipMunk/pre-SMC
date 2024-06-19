package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.FeedbackDao;
import com.SmartMed_Connect.entity.Feedback;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * FeedbackService 类
 * 该类提供了对反馈信息（Feedback 实体类）的增删改查服务。
 * 继承自 BaseService 类，实现了对 Feedback 实体的通用数据访问操作。
 */
@Service// 声明该类为 Spring 的服务类
public class FeedbackService extends BaseService<Feedback> {

    @Autowired
    protected FeedbackDao userDao;// 反馈信息的数据访问对象

    /**
     * 查询反馈信息
     * 根据传入的反馈信息对象 o 进行条件查询，返回符合条件的反馈信息列表。
     *
     * @param o 反馈信息对象，用于构建查询条件
     * @return 符合条件的反馈信息列表
     */
    @Override
    public List<Feedback> query(Feedback o) {
        QueryWrapper<Feedback> wrapper = new QueryWrapper();// 查询条件封装器
        if (Assert.notEmpty(o)) {// 判断对象 o 是否为空
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);// 将对象 o 转换为 Map
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;// 如果属性值为空，则跳过该属性
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));// 根据属性名和属性值添加查询条件
            }
        }
        return userDao.selectList(wrapper);// 执行查询并返回查询结果列表
    }


    /**
     * 查询所有反馈信息
     * 返回所有的反馈信息列表。
     *
     * @return 所有的反馈信息列表
     */
    @Override
    public List<Feedback> all() {
        return query(null);// 调用 query 方法查询所有反馈信息
    }


    /**
     * 保存反馈信息
     * 根据反馈信息对象 o 的 id 属性判断是新增还是更新操作，
     * 执行相应的持久化操作，并返回保存后的反馈信息对象。
     *
     * @param o 反馈信息对象，包含待保存的反馈信息
     * @return 保存后的反馈信息对象
     */
    @Override
    public Feedback save(Feedback o) {
        if (Assert.isEmpty(o.getId())) {
            userDao.insert(o);
        } else {
            userDao.updateById(o);
        }
        return userDao.selectById(o.getId());
    }

    /**
     * 获取反馈信息
     * 根据指定的 id 查询并返回对应的反馈信息对象。
     *
     * @param id 反馈信息对象的主键 id
     * @return 对应 id 的反馈信息对象，若不存在则返回 null
     */
    @Override
    public Feedback get(Serializable id) {
        return userDao.selectById(id);// 根据 id 查询反馈信息对象
    }

    /**
     * 删除反馈信息
     * 根据指定的 id 删除对应的反馈信息。
     *
     * @param id 反馈信息对象的主键 id
     * @return 删除操作影响的行数，若 id 不存在则返回 0
     */
    @Override
    public int delete(Serializable id) {
        return userDao.deleteById(id); // 根据 id 删除反馈信息
    }
}