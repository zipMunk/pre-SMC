package com.SmartMed_Connect.service;

import cn.hutool.core.map.MapUtil;
import com.SmartMed_Connect.dao.HistoryDao;
import com.SmartMed_Connect.entity.History;
import com.SmartMed_Connect.entity.IllnessKind;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SmartMed_Connect.utils.Assert;
import com.SmartMed_Connect.utils.BeanUtil;
import com.SmartMed_Connect.utils.VariableNameUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**
 * HistoryService 类
 * 提供了对浏览历史信息（History 实体类）的增删改查服务，以及一些特定的业务方法。
 * 继承自 BaseService 类，实现了对 History 实体的通用数据访问操作。
 */
@Service// 声明该类为 Spring 的服务类
public class HistoryService extends BaseService<History> {

    @Autowired
    protected HistoryDao historyDao;// 浏览历史信息的数据访问对象


    /**
     * 查询浏览历史信息
     * 根据传入的浏览历史信息对象 o 进行条件查询，返回符合条件的浏览历史信息列表。
     *
     * @param o 浏览历史信息对象，用于构建查询条件
     * @return 符合条件的浏览历史信息列表
     */
    @Override
    public List<History> query(History o) {
        QueryWrapper<History> wrapper = new QueryWrapper();// 查询条件封装器
        if (Assert.notEmpty(o)) { // 判断对象 o 是否为空
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);// 将对象 o 转换为 Map
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;// 如果属性值为空，则跳过该属性
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));// 根据属性名和属性值添加查询条件
            }
        }
        return historyDao.selectList(wrapper);// 执行查询并返回查询结果列表
    }


    /**
     * 查询所有浏览历史信息
     * 返回所有的浏览历史信息列表。
     *
     * @return 所有的浏览历史信息列表
     */
    @Override
    public List<History> all() {
        return query(null);
    }


    /**
     * 保存浏览历史信息
     * 根据浏览历史信息对象 o 的 id 属性判断是新增还是更新操作，
     * 执行相应的持久化操作，并返回保存后的浏览历史信息对象。
     *
     * @param o 浏览历史信息对象，包含待保存的浏览历史信息
     * @return 保存后的浏览历史信息对象
     */
    @Override
    public History save(History o) {
        if (Assert.isEmpty(o.getId())) { // 判断浏览历史信息对象的 id 属性是否为空
            historyDao.insert(o);// 执行插入操作
        } else {
            historyDao.updateById(o);// 执行更新操作
        }
        return historyDao.selectById(o.getId());// 根据 id 查询并返回保存后的浏览历史信息对象
    }


    /**
     * 获取浏览历史信息
     * 根据指定的 id 查询并返回对应的浏览历史信息对象。
     *
     * @param id 浏览历史信息对象的主键 id
     * @return 对应 id 的浏览历史信息对象，若不存在则返回 null
     */
    @Override
    public History get(Serializable id) {
        return historyDao.selectById(id);
    }


    /**
     * 删除浏览历史信息
     * 根据指定的 id 删除对应的浏览历史信息。
     *
     * @param id 浏览历史信息对象的主键 id
     * @return 删除操作影响的行数，若 id 不存在则返回 0
     */
    @Override
    public int delete(Serializable id) {
        return historyDao.deleteById(id);
    }

    /**
     * 插入单条浏览历史信息
     * 根据传入的用户 id、操作类型、名称值，插入一条新的浏览历史记录。
     *
     * @param uid       用户 id
     * @param type      操作类型
     * @param nameValue 名称值
     * @return 插入操作是否成功
     */
    public boolean insetOne(Integer uid, Integer type, String nameValue) {
        History history = new History();// 创建新的浏览历史信息对象
        history.setUserId(uid).setKeyword(nameValue).setOperateType(type); // 设置用户 id、操作类型、名称值
        return historyDao.insert(history) > 0;// 执行插入操作，并返回插入是否成功
    }

    /**
     * 查找用户最近的浏览历史信息列表
     * 根据用户 id 查询最近的浏览历史信息列表，并按照浏览时间降序排序。
     * 对重复的关键字进行去重，并进行特定字段的处理和补充。
     *
     * @param userId 用户 id
     * @return 用户最近的浏览历史信息列表
     */
    public List<Map<String, Object>> findList(Integer userId) {
        // 查询指定用户的浏览历史信息列表，按照创建时间降序排序
        List<History> list = historyDao.selectList(new QueryWrapper<History>().eq("user_id", userId)
                .orderByDesc("create_time"));
        // 去除重复的关键字记录，并按照创建时间降序排序
        List<History> histories = list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(History::getKeyword))), LinkedList::new));
        // 再次按照创建时间降序排序，并取前 10 条记录
        histories.sort((h1, h2) -> -h1.getCreateTime().compareTo(h2.getCreateTime()));
        List<History> historyList = histories.stream().limit(10).collect(Collectors.toList());

        // 构建返回的结果列表
        System.out.println(histories.size());
        List<Map<String, Object>> mapList = new LinkedList<>();
        historyList.forEach(his -> {
            Map<String, Object> map = cn.hutool.core.bean.BeanUtil.beanToMap(his);// 将 History 对象转换为 Map
            Integer operateType = MapUtil.getInt(map, "operateType");// 获取操作类型
            if (operateType == 1) {// 如果是类型为 1 的操作（假设 1 表示某种操作）
                List<String> keyword = Arrays.asList((MapUtil.getStr(map, "keyword")).split(","));// 将关键字按逗号分隔为列表
                IllnessKind illnessKind = illnessKindDao.selectById(Integer.valueOf(keyword.get(0)));// 查询疾病种类对象
                map.put("kind", illnessKind.getId());// 设置疾病种类的 id
                map.put("nameValue", keyword.get(1));// 设置名称值
                map.put("searchValue", illnessKind.getName() + ("无".equals(keyword.get(1)) ? "" : ("|" + keyword.get(1))));// 设置搜索值
            } else if (operateType == 2) {// 如果是类型为 2 的操作
                map.put("nameValue", MapUtil.getStr(map, "keyword"));// 设置名称值
                map.put("kind", "无");// 设置默认值
                map.put("searchValue", MapUtil.getStr(map, "keyword"));// 设置搜索值
            } else if (operateType == 3) {// 如果是类型为 3 的操作
                map.put("nameValue", MapUtil.getStr(map, "keyword"));// 设置名称值
                map.put("searchValue", MapUtil.getStr(map, "keyword"));// 设置搜索值
                map.put("kind", "无");// 设置默认值
            }
            mapList.add(map);// 将处理后的 Map 添加到结果列表中
        });
        return mapList;// 返回处理后的结果列表
    }
}