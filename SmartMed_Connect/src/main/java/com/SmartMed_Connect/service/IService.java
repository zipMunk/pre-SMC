package com.SmartMed_Connect.service;

import java.io.Serializable;
import java.util.List;


public interface IService<T> {

    /**
     * 保存一个实体对象
     *
     * @param t 要保存的实体对象
     * @return 保存后的实体对象
     */
    T save(T t);

    /**
     * 根据主键获取一个实体对象
     *
     * @param id 实体对象的主键
     * @return 获取到的实体对象
     */
    T get(Serializable id);

    /**
     * 根据主键删除一个实体对象
     *
     * @param id 实体对象的主键
     * @return 删除操作影响的行数（一般为1表示成功删除，0表示未找到）
     */
    int delete(Serializable id);

    /**
     * 根据条件查询实体对象列表
     *
     * @param o 查询条件
     * @return 符合条件的实体对象列表
     */
    List<T> query(T o);

    /**
     * 查询所有的实体对象
     *
     * @return 所有的实体对象列表
     */
    List<T> all();
}
