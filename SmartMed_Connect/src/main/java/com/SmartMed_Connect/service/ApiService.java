package com.SmartMed_Connect.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    int count = 0;

    public static GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .build();
    }

    public static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        return gen.call(param);
    }

    @Value("${ai-key}")
    private String apiKey;

    List<Message> messages = new ArrayList<>();

    public String query(String queryMessage) {
        Constants.apiKey = apiKey;
        try {
            if (count == 0) {
                messages.add(createMessage(Role.SYSTEM, "你是一名智能医生，请结合上下文，回答病人的问题，不许回答其他问题。"));
            }
            messages.add(createMessage(Role.USER, queryMessage));
            GenerationParam param = createGenerationParam(messages);
            GenerationResult result = callGenerationWithMessages(param);
            messages.add(result.getOutput().getChoices().get(0).getMessage());
            GenerationOutput output = result.getOutput();
            Message message = output.getChoices().get(0).getMessage();
            count++;
            return message.getContent();
            /*
            Generation gen = new Generation();
            MessageManager msgManager = new MessageManager(10);
            Message systemMsg = Message.builder().role(Role.SYSTEM.getValue()).content("你是智能医生，你只回答与医疗相关的问题，不要回答其他问题！").build();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage).build();
            msgManager.add(systemMsg);
            msgManager.add(userMsg);
            QwenParam param = QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get()).resultFormat(QwenParam.ResultFormat.MESSAGE).build();
            GenerationResult result = gen.call(param);
            GenerationOutput output = result.getOutput();
            Message message = output.getChoices().get(0).getMessage();
            return message.getContent();
            */
        } catch (Exception e) {
            return "发生错误，请稍后再试。";
        }
    }

    private static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }
}
