package com.SmartMed_Connect.dto;

import com.alibaba.fastjson.JSONObject;// 导入 fastjson 库用于 JSON 处理
import lombok.AllArgsConstructor;// Lombok 自动生成全参构造方法
import lombok.Builder;// Lombok 使用 Builder 模式来构建对象
import lombok.Data;// Lombok 自动生成 getter、setter、toString、hashCode 和 equals 方法
import lombok.NoArgsConstructor;// Lombok 自动生成无参构造方法
import com.SmartMed_Connect.utils.Assert;// 导入自定义的断言工具类，用于断言判断

import java.util.List;// 导入 Java 集合类 List

@Data // 自动生成 getter、setter、toString、hashCode 和 equals 方法
@Builder // 使用 Builder 模式来构建对象
@NoArgsConstructor // 自动生成无参构造方法
@AllArgsConstructor // 自动生成全参构造方法
public class RespResult {

    /**
     * 响应编码
     */
    protected String code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 响应数据
     */
    protected Object data;

    /**
     * 请求成功
     * @return 返回一个成功的 RespResult 对象，code 默认为 "SUCCESS"，message 默认为 "请求成功"
     */
    public static RespResult success() {
        return RespResult.builder()
                .code("SUCCESS")
                .message("请求成功")
                .build();
    }


    /**
     * 请求成功
     * @param message 成功消息
     * @return 返回一个成功的 RespResult 对象，code 默认为 "SUCCESS"，自定义 message
     */
    public static RespResult success(String message) {
        return RespResult.builder()
                .code("SUCCESS")
                .message(message)
                .build();
    }


    /**
     * 请求成功
     * @param message 成功消息
     * @param data 返回的数据对象
     * @return 返回一个成功的 RespResult 对象，code 默认为 "SUCCESS"，自定义 message 和 data
     */
    public static RespResult success(String message, Object data) {
        return RespResult.builder()
                .code("SUCCESS")
                .message(message)
                .data(data)
                .build();
    }


    /**
     * 请求失败
     * @return 返回一个失败的 RespResult 对象，code 默认为 "FAIL"，message 默认为 "请求失败"
     */
    public static RespResult fail() {
        return RespResult.builder()
                .code("FAIL")
                .message("请求失败")
                .build();
    }


    /**
     * 请求失败
     * @param message 失败消息
     * @return 返回一个失败的 RespResult 对象，code 默认为 "FAIL"，自定义 message
     */
    public static RespResult fail(String message) {
        return RespResult.builder()
                .code("FAIL")
                .message(message)
                .build();
    }

    /**
     * 未查询到数据
     * @param message 提示消息
     * @param data 未查询到数据时返回的相关数据
     * @return 返回一个未查询到数据的 RespResult 对象，code 默认为 "NOT_FOUND"，自定义 message 和 data
     */
    public static RespResult notFound(String message, Object data) {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 未查询到数据
     * @return 返回一个未查询到数据的 RespResult 对象，code 默认为 "NOT_FOUND"，message 默认为 "请求失败"
     */
    public static RespResult notFound() {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message("请求失败")
                .build();
    }


    /**
     * 未查询到数据
     * @param message 提示消息
     * @return 返回一个未查询到数据的 RespResult 对象，code 默认为 "NOT_FOUND"，自定义 message
     */
    public static RespResult notFound(String message) {
        return RespResult.builder()
                .code("NOT_FOUND")
                .message(message)
                .build();
    }

    /**
     * 请求失败
     * @param message 失败消息
     * @param data 失败时返回的相关数据
     * @return 返回一个请求失败的 RespResult 对象，code 默认为 "FAIL"，自定义 message 和 data
     */
    public static RespResult fail(String message, Object data) {
        return RespResult.builder()
                .code("FAIL")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 判断请求是否成功
     * @return 如果 code 等于 "SUCCESS" 返回 true，否则返回 false
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(code);
    }

    /**
     * 判断请求是否成功并且有数据返回
     * @return 如果 code 等于 "SUCCESS" 并且 data 不为空，返回 true，否则返回 false
     */
    public boolean isSuccessWithDateResp() {
        return "SUCCESS".equals(code) && Assert.notEmpty(data);
    }

    /**
     * 判断请求是否不成功
     * @return 如果 code 不等于 "SUCCESS" 返回 true，否则返回 false
     */
    public boolean notSuccess() {
        return !isSuccess();
    }

    /**
     * 获取响应的数据集合
     * @param clazz 数据对象的类类型
     * @param <T> 数据对象的泛型类型
     * @return 返回解析后的数据对象列表
     */
    public <T> List<T> getDataList(Class<T> clazz) {
        String jsonString = JSONObject.toJSONString(data);//将 data 对象转换为 JSON 字符串。
        return JSONObject.parseArray(jsonString, clazz);//将 JSON 字符串 jsonString 解析为一个 List，其中每个元素的类型由 clazz 参数指定。
    }

    /**
     * 获取响应的数据
     * @param clazz 数据对象的类类型
     * @param <T> 数据对象的泛型类型
     * @return 返回解析后的数据对象
     */
    public <T> T getDataObj(Class<T> clazz) {
        String jsonString = JSONObject.toJSONString(data);
        return JSONObject.parseObject(jsonString, clazz);
    }

}
