package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController<User> {

    /**
     * 发送消息
     */
    @PostMapping("/query")
    public RespResult query(String content) {
        String result = apiService.query(content);
        return RespResult.success(result);
    }
}
