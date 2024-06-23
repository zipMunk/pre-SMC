package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.PatientHistoryDao;
import com.SmartMed_Connect.entity.PatientHistory;
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
public class PatientHistoryService extends BaseService<PatientHistory> {

    /**
     * 根据条件查询 PatientHistory 对象列表
     *
     * @param o 查询条件封装的 PatientHistory 对象
     * @return 符合条件的 PatientHistory 对象列表
     */
    @Override
    public List<PatientHistory> query(PatientHistory o)
    {
        //创建查询条件的包装器（QueryWrapper），用于构建数据库查询条件
        QueryWrapper<PatientHistory> wrapper = new QueryWrapper();
        if(Assert.notEmpty(o))
        {
            // 将对象转换为 Map 形式
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for(String key : bean2Map.keySet())
            {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                    // 跳过空值字段
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return patientHistoryDao.selectList(wrapper);
    }

    /**
     * 查询所有的 PatientHistory 对象
     *
     * @return 所有的 PatientHistory 对象列表
     */
    @Override
    public List<PatientHistory> all()
    {
        return query(null);
    }

    /**
     * 保存或更新一个 PatientHistory 对象
     *
     * @param o 要保存的 PatientHistory 对象
     * @return 保存后的 PatientHistory 对象
     */
    @Override
    public PatientHistory save(PatientHistory o)
    {
        // 如果 ID 为空，则插入新的对象
        if(Assert.isEmpty(o.getId()))
        {
            System.out.println("插入病历");
            patientHistoryDao.insert(o);
        }else {
            // 如果 ID 不为空，则更新已有对象
            System.out.println("跟新病历");
            patientHistoryDao.updateById(o);
        }

        return patientHistoryDao.selectById(o.getId());
    }

    /**
     * 根据 ID 获取一个 PatientHistory 对象
     *
     * @param id 对象的主键 ID
     * @return 获取到的 PatientHistory 对象
     */
    @Override
    public PatientHistory get(Serializable id)
    {
        return patientHistoryDao.selectById(id);
    }

    /**
     * 根据 ID 删除一个 PatientHistory 对象
     *
     * @param id 要删除的对象的主键 ID
     * @return 删除操作影响的行数（一般为1表示成功删除，0表示未找到）
     */
    @Override
    public int delete(Serializable id)
    {
        return patientHistoryDao.deleteById(id);
    }


    /**
     * 根据 UserId 查询该用户所有的病史
     *
     * @param userId 用户ID
     * @return 符合条件的 PatientHistory 对象列表
     */
    public List<PatientHistory> findByUserId(Integer userId) {
        if (Assert.notEmpty(userId)) {
            QueryWrapper<PatientHistory> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            return patientHistoryDao.selectList(wrapper);

        }
        return null;
    }
}
