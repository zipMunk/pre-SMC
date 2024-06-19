package com.SmartMed_Connect.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * 断言工具类，用于判断对象、集合、字符串等是否为空，并提供断言方法抛出异常
 */
public class Assert {

    /**
     * 判断 CharSequence 是否为空（包括 null 和空字符串）
     *
     * @param s 要判断的 CharSequence 对象
     * @return 如果 CharSequence 为 null 或者长度为 0，则返回 true；否则返回 false
     */
    public static boolean isEmpty(CharSequence s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        // 如果字符串中有非空格字符，则返回 false
        for (int i = 0; i < s.length(); ++i) {
            if (' ' != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断 Collection 是否为空（包括 null 和空集合）
     *
     * @param obj 要判断的 Collection 对象
     * @return 如果 Collection 为 null 或者空集合，则返回 true；否则返回 false
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 判断 Map 是否为空（包括 null 和空 Map）
     *
     * @param obj 要判断的 Map 对象
     * @return 如果 Map 为 null 或者空 Map，则返回 true；否则返回 false
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 判断数组是否为空（包括 null 和空数组）
     *
     * @param obj 要判断的数组对象
     * @return 如果数组为 null 或者长度为 0，则返回 true；否则返回 false
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * 判断对象是否为空（包括 null）
     *
     * @param obj 要判断的对象
     * @return 如果对象为 null，则返回 true；否则返回 false
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * 判断 List 是否为空（包括 null 和空 List）
     *
     * @param obj 要判断的 List 对象
     * @return 如果 List 为 null 或者空 List，则返回 true；否则返回 false
     */
    public static boolean isEmpty(List<?> obj) {
        return obj == null || obj.size() == 0;
    }

    /**
     * 判断 CharSequence 是否不为空（非空包括有内容的字符串）
     *
     * @param s 要判断的 CharSequence 对象
     * @return 如果 CharSequence 不为 null 且长度不为 0，则返回 true；否则返回 false
     */
    public static boolean notEmpty(CharSequence s) {
        return !isEmpty(s);
    }

    /**
     * 判断 Collection 是否不为空（非空包括非空集合）
     *
     * @param obj 要判断的 Collection 对象
     * @return 如果 Collection 不为 null 且不为空集合，则返回 true；否则返回 false
     */
    public static boolean notEmpty(Collection<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断 Map 是否不为空（非空包括非空 Map）
     *
     * @param obj 要判断的 Map 对象
     * @return 如果 Map 不为 null 且不为空 Map，则返回 true；否则返回 false
     */
    public static boolean notEmpty(Map<?, ?> obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断数组是否不为空（非空包括非空数组）
     *
     * @param obj 要判断的数组对象
     * @return 如果数组不为 null 且长度大于 0，则返回 true；否则返回 false
     */
    public static boolean notEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断对象是否不为空（非空包括非 null 对象）
     *
     * @param obj 要判断的对象
     * @return 如果对象不为 null，则返回 true；否则返回 false
     */
    public static boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断 List 是否不为空（非空包括非空 List）
     *
     * @param obj 要判断的 List 对象
     * @return 如果 List 不为 null 且不为空 List，则返回 true；否则返回 false
     */
    public static boolean notEmpty(List<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * 断言 CharSequence 不为空，如果为空则抛出 RuntimeException 异常
     *
     * @param s    要断言的 CharSequence 对象
     * @param name 异常信息中的参数名称
     * @throws RuntimeException 如果 CharSequence 为空，则抛出异常
     */
    public static void assertNotEmpty(CharSequence s, String name) {
        if (isEmpty(s)) {
            throwException(name);
        }
    }

    public static void assertNotEmpty(Collection<?> obj, String name) {
        if (isEmpty(obj)) {
            throwException(name);
        }
    }

    public static void assertNotEmpty(Map<?, ?> obj, String name) {
        if (isEmpty(obj)) {
            throwException(name);
        }
    }

    public static void assertNotEmpty(Object[] obj, String name) {
        if (isEmpty(obj)) {
            throwException(name);
        }
    }

    public static void assertNotEmpty(Object obj, String name) {
        if (isEmpty(obj)) {
            throwException(name);
        }
    }

    public static void assertNotEmpty(List<?> obj, String name) {
        if (isEmpty(obj)) {
            throwException(name);
        }
    }

    /**
     * 抛出 RuntimeException 异常，异常信息为请求参数为空的提示信息
     *
     * @param name 异常信息中的参数名称
     * @return 不返回，直接抛出异常
     * @throws RuntimeException 始终抛出 RuntimeException 异常
     */
    private static String throwException(String name) {
        throw new RuntimeException("REQUEST_PARAM_IS_NULL 请求参数<" + name + ">为空");
    }

}
