package com.SmartMed_Connect.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.SmartMed_Connect.dao.IllnessDao;
import com.SmartMed_Connect.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SmartMed_Connect.entity.*;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IllnessService extends BaseService<Illness> {

    @Autowired
    protected IllnessDao illnessDao;

    @Override
    public List<Illness> query(Illness o) {
        QueryWrapper<Illness> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return illnessDao.selectList(wrapper);
    }

    @Override
    public List<Illness> all() {
        return query(null);
    }

    @Override
    public Illness save(Illness o) {
        if (Assert.isEmpty(o.getId())) {
            illnessDao.insert(o);
        } else {
            illnessDao.updateById(o);
        }
        return illnessDao.selectById(o.getId());
    }

    @Override
    public Illness get(Serializable id) {
        return illnessDao.selectById(id);
    }

    @Override
    public int delete(Serializable id) {
        return illnessDao.deleteById(id);
    }

    /**
     *  查找疾病列表的方法，根据疾病类别、名称和分页参数进行查询
      */
    public Map<String, Object> findIllness(Integer kind, String illnessName, Integer page) {
        // 创建一个初始容量为 4 的 HashMap，用于存储最终结果
        Map<String, Object> map = new HashMap<>(4);
        // 创建一个 QueryWrapper 对象，用于构建查询条件
        QueryWrapper<Illness> illnessQueryWrapper = new QueryWrapper<>();
        // 如果 illnessName 非空，则添加模糊查询条件
        if (Assert.notEmpty(illnessName)) {
            illnessQueryWrapper
                    .like("illness_name", illnessName)// 疾病名称包含 illnessName
                    .or()
                    .like("include_reason", illnessName)// 包含原因包含 illnessName
                    .or()
                    .like("illness_symptom", illnessName)// 疾病症状包含 illnessName
                    .or()
                    .like("special_symptom", illnessName);// 特殊症状包含 illnessName
        }
        // 如果 kind 非空，则添加疾病种类的查询条件
        if (kind != null) {
            if (Assert.notEmpty(illnessName)) {
                // 如果 illnessName 非空，且 kind 非空，则添加种类 ID 条件，并按创建时间降序排序，同时进行分页
                illnessQueryWrapper.last("and (kind_id = " + kind + ") ORDER BY create_time DESC limit " + (page - 1) * 9 + "," + page * 9);
            } else {
                // 如果 illnessName 为空，但 kind 非空，则仅按种类 ID 查询，并按创建时间降序排序，同时进行分页
                illnessQueryWrapper.eq("kind_id", kind);
                illnessQueryWrapper.orderByDesc("create_time");
                illnessQueryWrapper.last("limit " + (page - 1) * 9 + "," + page * 9);
            }
        } else {
            illnessQueryWrapper.orderByDesc("create_time");
            illnessQueryWrapper.last("limit " + (page - 1) * 9 + "," + page * 9);
        }
        // 获取符合条件的疾病记录的总数
        int size = illnessDao.selectMaps(illnessQueryWrapper).size();
        //对查询出来的疾病列表进行处理
        List<Map<String, Object>> list = illnessDao.selectMaps(illnessQueryWrapper);
        list.forEach(l -> {
            Integer id = MapUtil.getInt(l, "id");// 获取疾病 ID
            Pageview pageInfo = pageviewDao.selectOne(new QueryWrapper<Pageview>().eq("illness_id", id));// 获取对应的页面浏览信息
            l.put("kindName", "暂无归属类");// 初始化 kindName 为 "暂无归属类"
            l.put("create_time", MapUtil.getDate(l, "create_time"));// 格式化创建时间
            l.put("pageview", pageInfo == null ? 0 : pageInfo.getPageviews());// 添加页面浏览量信息
            Integer kindId = MapUtil.getInt(l, "kind_id");// 获取疾病种类 ID
            if (Assert.notEmpty(kindId)) {
                IllnessKind illnessKind = illnessKindDao.selectById(kindId);// 根据种类 ID 查询对应的疾病种类
                if (Assert.notEmpty(illnessKind)) {
                    l.put("kindName", illnessKind.getName());// 如果疾病种类存在，则更新 kindName
                }
            }
        });
        map.put("illness", list);
        map.put("size", size < 9 ? 1 : size / 9 + 1);
        return map;
    }

    public Map<String, Object> findIllnessOne(Integer id) {
        // 查询 Illness 表，根据 ID 查找疾病
        Illness illness = illnessDao.selectOne(new QueryWrapper<Illness>().eq("id", id));
        // 查询 IllnessMedicine 表，找到所有与该疾病 ID 相关的药物
        List<IllnessMedicine> illnessMedicines = illnessMedicineDao.selectList(new QueryWrapper<IllnessMedicine>().eq("illness_id", id));
        // 初始化一个列表来存储药物
        List<Medicine> list = new ArrayList<>(4);
        // 初始化一个 Map 来存储返回结果
        Map<String, Object> map = new HashMap<>(4);
        // 查询 Pageview 表，根据疾病 ID 查找页面浏览记录
        Pageview illness_id = pageviewDao.selectOne(new QueryWrapper<Pageview>().eq("illness_id", id));
        // 如果没有找到对应的页面浏览记录，创建一个新的记录并插入数据库
        if (Assert.isEmpty(illness_id)) {
            illness_id = new Pageview();
            illness_id.setIllnessId(id);
            illness_id.setPageviews(1);
            pageviewDao.insert(illness_id);
        } else {
            // 如果找到了对应的页面浏览记录，更新浏览次数并保存
            illness_id.setPageviews(illness_id.getPageviews() + 1);
            pageviewDao.updateById(illness_id);
        }
        // 将疾病信息放入返回结果的 Map 中
        map.put("illness", illness);

        // 如果找到了相关的药物，将每个药物的信息放入列表中
        if (CollUtil.isNotEmpty(illnessMedicines)) {
            illnessMedicines.forEach(illnessMedicine -> {
                // 查询 Medicine 表，根据药物 ID 查找药物
                Medicine medicine = medicineDao.selectOne(new QueryWrapper<Medicine>().eq("id", illnessMedicine.getMedicineId()));
                // 如果药物不为空，将其添加到列表中
                if (ObjectUtil.isNotNull(medicine)) {
                    list.add(medicine);
                }
            });
            // 将药物列表放入返回结果的 Map 中
            map.put("medicine", list);

        }
        // 返回包含疾病和药物信息的 Map
        return map;
    }

    public Illness getOne(QueryWrapper<Illness> queryWrapper) {
        return illnessDao.selectOne(queryWrapper);
    }
}