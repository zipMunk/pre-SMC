package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.MedicineDao;
import com.SmartMed_Connect.entity.Medicine;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicineService extends BaseService<Medicine> {

    @Autowired
    protected MedicineDao medicineDao;

    /**
     * 根据条件查询 Medicine 对象列表
     *
     * @param o 查询条件封装的 Medicine 对象
     * @return 符合条件的 Medicine 对象列表
     */
    @Override
    public List<Medicine> query(Medicine o) {
        QueryWrapper<Medicine> wrapper = new QueryWrapper();
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
        return medicineDao.selectList(wrapper);
    }

    /**
     * 查询所有的 Medicine 对象
     *
     * @return 所有的 Medicine 对象列表
     */
    @Override
    public List<Medicine> all() {
        return query(null);
    }

    /**
     * 保存或更新一个 Medicine 对象
     *
     * @param o 要保存的 Medicine 对象
     * @return 保存后的 Medicine 对象
     */
    @Override
    public Medicine save(Medicine o) {
        // 如果 ID 为空，则插入新的对象
        if (Assert.isEmpty(o.getId())) {
            medicineDao.insert(o);
        } else {
            // 如果 ID 不为空，则更新已有对象
            medicineDao.updateById(o);
        }
        return medicineDao.selectById(o.getId());
    }

    /**
     * 根据 ID 获取一个 Medicine 对象
     *
     * @param id 对象的主键 ID
     * @return 获取到的 Medicine 对象
     */
    @Override
    public Medicine get(Serializable id) {
        return medicineDao.selectById(id);
    }

    /**
     * 根据 ID 删除一个 Medicine 对象
     *
     * @param id 要删除的对象的主键 ID
     * @return 删除操作影响的行数（一般为1表示成功删除，0表示未找到）
     */
    @Override
    public int delete(Serializable id) {
        return medicineDao.deleteById(id);
    }

    /**
     * 根据药品名称或关键字模糊查询药品列表，并分页返回结果
     *
     * @param nameValue 药品名称或关键字
     * @param page      分页页码
     * @return 包含药品列表和分页信息的 Map
     */
    public Map<String, Object> getMedicineList(String nameValue, Integer page) {

        List<Medicine> medicineList = null;
        Map<String, Object> map = new HashMap<>(4);
        // 根据传入的名称或关键字进行模糊查询，支持分页
        if (Assert.notEmpty(nameValue)) {
            medicineList = medicineDao.selectList(new QueryWrapper<Medicine>().
                    like("medicine_name", nameValue)
                    .or().like("keyword", nameValue)
                    .or().like("medicine_effect", nameValue)
                    .last("limit " + (page - 1) * 9 + "," + page * 9));
        } else {
            medicineList = medicineDao.selectList(new QueryWrapper<Medicine>()
                    .last("limit " + (page - 1) * 9 + "," + page * 9));
        }
        // 将查询结果放入返回的 Map 中
        map.put("medicineList", medicineList);
        // 计算分页总数，并放入返回的 Map 中
        map.put("size", medicineList.size() < 9 ? 1 : medicineList.size() / 9 + 1);
        return map;
    }
}