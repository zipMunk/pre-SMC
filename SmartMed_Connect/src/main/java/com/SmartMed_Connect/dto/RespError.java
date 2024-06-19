package com.SmartMed_Connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 继承自 RespResult 的异常响应DTO
 * 扩展了请求地址和异常类两个字段
 */
//RespError 用于在发生异常时，返回更详细的错误信息，包括请求的地址和异常的类名，有助于开发人员定位和解决问题。
@Data // 自动生成 getter、setter、toString、hashCode 和 equals 方法
@NoArgsConstructor // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参构造方法
@EqualsAndHashCode(callSuper = true)  // 调用父类 RespResult 的 equals 和 hashCode 方法
public class RespError extends RespResult {

    /**
     * 请求地址（发生异常时返回）
     */
    private String requestUrl;

    /**
     * 异常类（发生异常时返回）
     */
    private String exception;
}



