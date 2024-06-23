package com.SmartMed_Connect.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

public class BeanUtil {

    // 使用静态变量来缓存 BeanCopier 实例，提升性能
    private static Map<String, BeanCopier> beanCopierMap = new HashMap();

    /**
     * 将源对象的属性拷贝到目标类的新实例中
     * @param src 源对象
     * @param clazz 目标类的 Class 对象
     * @param <T> 目标类的类型
     * @return 目标类的实例
     * @throws InstantiationException 如果无法实例化目标类
     * @throws IllegalAccessException 如果目标类的构造方法不可访问
     */
    public static <T> T copy(Object src, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        if ((null == src) || (null == clazz)) return null;
        Object des = clazz.newInstance();
        copy(src, des);
        return (T) des;
    }


    public static void copy(Object src, Object des) {
        if ((null == src) || (null == des)) return;
        // 生成用于在缓存中查找 BeanCopier 的唯一键
        String key = generateKey(src.getClass(), des.getClass());
        // 获取或创建 BeanCopier 实例
        BeanCopier copier = (BeanCopier) beanCopierMap.get(key);
        // 如果缓存中不存在该 BeanCopier 实例，创建新的 BeanCopier
        if (null == copier) {
            // 创建一个新的 BeanCopier，用于将 src 的属性拷贝到 des 中
            copier = BeanCopier.create(src.getClass(), des.getClass(), false);
            beanCopierMap.put(key, copier);
        }
        // 执行属性拷贝
        copier.copy(src, des, null);
    }


    /**
     * 将 Map 对象转换为指定类的实例对象，并将 Map 中的键值对映射到对象的属性上。
     *
     * @param map   包含属性值的 Map 对象，键为属性名，值为属性值
     * @param clazz 目标类的 Class 对象，表示要转换成的对象类型
     * @param <T>   泛型，表示目标类的类型
     * @return 转换后的目标类的实例对象，如果 map 或 clazz 为 null，则返回 null
     * @throws InstantiationException 如果无法实例化目标类，则抛出该异常
     * @throws IllegalAccessException 如果无法访问目标类的构造方法或发生安全性异常，则抛出该异常
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        if ((null == map) || (null == clazz)) return null;
        T bean = clazz.newInstance();
        // 调用下面的 map2Bean 方法将 map 中的键值对映射到 bean 的属性上
        map2Bean(map, bean);
        // 返回转换后的目标类的实例对象
        return bean;
    }


    public static <T> void map2Bean(Map<String, Object> map, T bean) {
        if ((null == map) || (null == bean)) return;
        // 使用 BeanMap 创建目标对象的映射视图
        BeanMap beanMap = BeanMap.create(bean);
        // 将 Map 中的键值对映射到 bean 的属性上
        beanMap.putAll(map);
    }

    /**
     * 将 Java 对象（bean）转换为包含其属性名和属性值的 Map 对象。
     *
     * @param bean 要转换的 Java 对象
     * @return 包含 bean 属性名和属性值的 Map 对象，如果 bean 为 null，则返回 null
     */
    public static Map<String, Object> bean2Map(Object bean) {
        if (null == bean) return null;
        // 调用 copy 方法将 BeanMap 转换为普通的 Map<String, Object>
        return copy(BeanMap.create(bean));
    }


    /**
     * 将包含多个 Java 对象的列表（beanList）转换为包含这些对象属性名和属性值的 List<Map<String, Object>>。
     *
     * @param beanList 包含多个 Java 对象的列表
     * @param <T>      Java 对象的类型
     * @return 包含 beanList 中每个对象属性名和属性值的 List<Map<String, Object>> 对象，
     * 如果 beanList 为 null 或空列表，则返回 null
     */
    public static <T> List<Map<String, Object>> mapList(List<T> beanList) {
        // 如果 beanList 为 null 或空列表，则直接返回 null
        if ((null == beanList) || (beanList.size() < 1)) return null;
        // 创建一个新的 ArrayList，用于存储转换后的 Map 对象列表
        List<Map<String, Object>> mapList = new ArrayList();
        int i = 0;
        for (int size = beanList.size(); i < size; i++) {
            mapList.add(bean2Map(beanList.get(i)));
        }
        return mapList;
    }

    public static <K, V> Map<K, V> copy(Map<K, V> src) {
        if (null == src) return null;
        // 创建一个新的 HashMap 对象 des，用于存储复制后的键值对
        Map<K, V> des = new HashMap();
        // 将 src 中的所有键值对复制到 des 中
        des.putAll(src);
        // 返回复制后的新的 HashMap 对象 des
        return des;
    }


    /**
     * 将多个源对象（srcs）的属性值应用到目标对象（des）中，仅当目标对象中相应属性的值为 null 时才进行赋值。
     *
     * @param des  目标对象，属性值可能被设置
     * @param srcs 多个源对象数组，从这些对象中获取属性值
     */
    public static void apply(Object des, Object... srcs) {
        // 如果目标对象 des 为 null，或者源对象数组 srcs 为 null 或空数组，则直接返回，不进行任何操作
        if ((null == des) || (null == srcs) || (srcs.length < 1)) return;
        // 创建目标对象的 BeanMap，用于获取和设置目标对象的属性值
        BeanMap desBeanMap = BeanMap.create(des);
        // 获取目标对象的所有键（即属性名）
        Set<?> keys = desBeanMap.keySet();
        BeanMap srcBeanMap = null;
        // 循环处理每个源对象
        for (Object src : srcs) {
            if (null != src) {
                // 创建源对象的 BeanMap，用于获取源对象的属性值
                srcBeanMap = BeanMap.create(src);
                // 遍历目标对象的所有属性名
                for (Object key : keys) {
                    // 获取源对象中与当前属性名对应的属性值
                    Object value = srcBeanMap.get(key);
                    // 如果源对象的属性值不为 null，并且目标对象的相应属性值为 null，则将源对象的属性值赋给目标对象
                    if ((null != value) && (null == desBeanMap.get(key))) {
                        desBeanMap.put(des, key, value);
                    }
                }
            }
        }
    }


    /**
     * 将多个属性名-属性值映射的 Map 对象列表（srcList）的属性值应用到目标对象（des）中。
     * 如果目标对象中已有相应属性的值，则将该属性值从列表中的 Map 对象中复制到目标对象中。
     *
     * @param des     目标对象，属性值可能被设置
     * @param srcList 包含属性名-属性值映射的 Map 对象列表
     */
    public static void apply(Object des, List<Map<String, Object>> srcList) {
        Map<String, Object> src;
        // 如果目标对象 des 为 null，或者源对象列表 srcList 为 null 或空列表，则直接返回，不进行任何操作
        if ((null == des) || (null == srcList) || (srcList.isEmpty())) return;
        // 创建目标对象的 BeanMap，用于获取和设置目标对象的属性值
        BeanMap desBeanMap = BeanMap.create(des);
        // 获取目标对象的所有属性名集合
        Set<?> keys = desBeanMap.keySet();
        // 遍历源对象列表 srcList
        for (Iterator localIterator1 = srcList.iterator(); localIterator1.hasNext(); ) {
            src = (Map) localIterator1.next();
            // 如果当前源对象 src 不为 null，并且不为空（即包含至少一个属性名-属性值映射）
            if ((null != src) && (!src.isEmpty())) {
                // 遍历目标对象的所有属性名
                for (Object key : keys) {
                    // 获取当前属性名在源对象 src 中对应的属性值
                    Object value = src.get(key);
                    // 如果源对象 src 中的属性值不为 null，则将其复制到目标对象 des 中
                    if (null != value) {
                        desBeanMap.put(des, key, value);
                    }
                }
            }
        }
    }

    /**
     * 生成用于标识 BeanCopier 实例的唯一键。
     * 键的格式为 "src类名des类名"。
     *
     * @param src 源类的 Class 对象
     * @param des 目标类的 Class 对象
     * @return 标识 BeanCopier 实例的唯一键
     */
    private static String generateKey(Class<?> src, Class<?> des) {
        return src.getName() + des.getName();
    }

    /**
     * 将 Bean 对象转换为字符串表示。
     *
     * @param value 要转换的 Bean 对象
     * @param <T>   Bean 对象的类型
     * @return Bean 对象的字符串表示，如果对象为 null，则返回 null
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        // 如果 Bean 对象是 Integer 类型
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {// 如果 Bean 对象是其他类型，使用 JSON 序列化将其转换为 JSON 字符串
            return JSON.toJSONString(value);
        }
    }


    /**
     * 将字符串转换为指定类型的 Bean 对象。
     *
     * @param str   要转换的字符串
     * @param clazz 目标 Bean 类型的 Class 对象
     * @param <T>   目标 Bean 的类型
     * @return 转换后的 Bean 对象，如果字符串或 Class 对象为 null 或字符串为空，则返回 null
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        // 如果目标类型是 Integer 类型
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            // 如果目标类型是其他类型，使用 JSON 反序列化将字符串转换为指定类型的对象
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
