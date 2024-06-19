package com.SmartMed_Connect.service;

import com.SmartMed_Connect.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.SmartMed_Connect.dao.*;
import org.springframework.stereotype.Service;

/**
 * BaseService 抽象类
 * 该类是所有服务类的基础类，实现了 IService 接口，定义了一些通用的数据访问操作。
 * 泛型 T 表示服务类操作的实体类型。
 */
@Service
public abstract class BaseService<T> implements IService<T> {

    /**
     * 用户 DAO 对象，用于访问用户相关数据的持久化操作。
     */
    @Autowired
    protected UserDao userDao;

    /**
     * 浏览历史 DAO 对象，用于访问浏览历史相关数据的持久化操作。
     */
    @Autowired
    protected HistoryDao historyDao;

    /**
     * 疾病 DAO 对象，用于访问疾病相关数据的持久化操作。
     */
    @Autowired
    protected IllnessDao illnessDao;

    /**
     * 疾病种类 DAO 对象，用于访问疾病种类相关数据的持久化操作。
     */
    @Autowired
    protected IllnessKindDao illnessKindDao;

    /**
     * 疾病相关药物 DAO 对象，用于访问疾病与药物关联数据的持久化操作。
     */
    @Autowired
    protected IllnessMedicineDao illnessMedicineDao;

    /**
     * 药物 DAO 对象，用于访问药物相关数据的持久化操作。
     */
    @Autowired
    protected MedicineDao medicineDao;

    /**
     * 医疗新闻 DAO 对象，用于访问医疗新闻相关数据的持久化操作。
     */
    @Autowired
    protected MedicalNewsDao medicalNewsDao;

    /**
     * 页面浏览量 DAO 对象，用于访问页面浏览量相关数据的持久化操作。
     */
    @Autowired
    protected PageviewDao pageviewDao;

}
