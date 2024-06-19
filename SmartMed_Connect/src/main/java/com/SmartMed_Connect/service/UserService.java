package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.UserDao;
import com.SmartMed_Connect.entity.User;
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
public class UserService extends BaseService<User> {

    @Autowired
    protected UserDao userDao;

    /**
     * 根据条件查询 User 对象列表
     *
     * @param o 查询条件封装的 User 对象
     * @return 符合条件的 User 对象列表
     */
    @Override
    public List<User> query(User o) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            // 将对象转换为 Map 形式
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;// 跳过空值字段
                }
                // 使用下划线形式的字段名查询
                //VariableNameUtils.humpToLine(key) 是一个工具方法，用于将驼峰命名法的属性名转换为下划线分隔的数据库字段名。
                // 例如，如果 key 是 "userName"，则转换后变为 "user_name"。
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return userDao.selectList(wrapper);
    }

    /**
     * 查询所有的 User 对象
     *
     * @return 所有的 User 对象列表
     */
    @Override
    public List<User> all() {
        return query(null);
    }

    /**
     * 保存或更新一个 User 对象
     *
     * @param o 要保存的 User 对象
     * @return 保存后的 User 对象
     */
    @Override
    public User save(User o) {
        // 如果 ID 为空，则插入新的对象
        if (Assert.isEmpty(o.getId())) {
            userDao.insert(o);
        } else {
            // 如果 ID 不为空，则更新已有对象
            userDao.updateById(o);
        }
        return userDao.selectById(o.getId());
    }

    /**
     * 根据 ID 获取一个 User 对象
     *
     * @param id 对象的主键 ID
     * @return 获取到的 User 对象
     */
    @Override
    public User get(Serializable id) {
        return userDao.selectById(id);
    }

    /**
     * 根据 ID 删除一个 User 对象
     *
     * @param id 要删除的对象的主键 ID
     * @return 删除操作影响的行数（一般为1表示成功删除，0表示未找到）
     */
    @Override
    public int delete(Serializable id) {
        return userDao.deleteById(id);
    }
}