package com.SmartMed_Connect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.SmartMed_Connect.entity.Feedback;
/**
 * 反馈信息数据访问接口
 * 继承自 MyBatis-Plus 的 BaseMapper 接口，提供了基本的 CRUD 方法。
 */
@Repository

public interface FeedbackDao extends BaseMapper<Feedback> {

}
