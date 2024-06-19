package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * MessageController 类是处理消息相关请求的控制器
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController<User> {

    /**
     * 发送消息查询请求
     *
     * @param content 消息内容
     * @return 返回 RespResult 对象，包含查询结果或错误信息
     */
    @PostMapping("/query")
    public RespResult query(String content) {
        // 调用 apiService 的 query 方法，处理消息查询请求
        String result = apiService.query(content);
        // 将查询结果封装为成功的 RespResult 对象，并返回给客户端
        return RespResult.success(result);
    }
}
