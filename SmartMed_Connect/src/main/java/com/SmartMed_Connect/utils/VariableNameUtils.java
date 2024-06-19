package com.SmartMed_Connect.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableNameUtils {

    // 用于匹配驼峰命名法中的大写字母的正则表达式
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    // 用于匹配下划线命名法中的下划线及其后的一个字母的正则表达式
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     * 将下划线命名法的字符串转换为驼峰命名法的字符串。
     * 例如： "hello_world" -> "helloWorld"
     *
     * @param str 下划线命名法的字符串
     * @return 驼峰命名法的字符串
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);// 使用正则表达式创建一个匹配器
        StringBuffer sb = new StringBuffer();// 用于存储转换后的字符串
        // 遍历匹配项
        while (matcher.find()) {
            // 将下划线及其后的一个字母替换为该字母的大写形式
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        // 将剩余部分添加到结果字符串中
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}