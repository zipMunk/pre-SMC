package com.SmartMed_Connect.service;

import com.SmartMed_Connect.controller.UserController;
import com.SmartMed_Connect.dao.ChatMessageDao;
import com.SmartMed_Connect.dto.MessageWrapper;
import com.SmartMed_Connect.entity.ChatMessage;
import com.SmartMed_Connect.entity.PatientHistory;
import com.SmartMed_Connect.utils.ClientUtil;
import com.SmartMed_Connect.utils.IpUtil;
import com.SmartMed_Connect.utils.MapUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MingAPIService {


    @Autowired
    protected PatientHistoryService patientHistoryService;

    @Autowired
    private UserController userController;

    @Autowired
    protected UserService userService;

    @Autowired
    private ChatMessageDao chatMessageMapper;
    @Autowired
    private HttpServletRequest request;
    @Value("${ming.url}")
    String mingURL;
    private final List<MessageWrapper> messageCacheList = new ArrayList<>();

    private String createQueryContent(Integer userId,String queryMessage){

        PatientHistory patientHistory = patientHistoryService.findByUserId(userId).get(0);

        String historyString = patientHistory.toString();
        String patientHistoryInfo = "病人的以往病史是："+historyString+"请你结合这些信息给出相应的建议";

        return queryMessage+patientHistoryInfo;


    }
    public String query(String queryMessage){
        Map<String,Object> params = new HashMap<>();

        params.put("model","ming-moe-4B");
        params.put("n",1);
        params.put("temperature",0.5);
        params.put("max_tokens",3072);

        Integer userId = userController.loginUser.getId();
        String queryContent = queryMessage;
//        List<MessageWrapper> messages;
        if(messageCacheList.isEmpty()){
            fetchUserChatHistory(userId);
            if(messageCacheList.isEmpty()){
                queryContent = createQueryContent(userId,queryMessage);
            }
        }


        if(queryMessage.equals("/newchat")){
            messageCacheList.clear();
            clearUserChatHistory(userId);
            return "已清空聊天记录";
        }

        MessageWrapper userMessage = new MessageWrapper("user",queryContent);
        messageCacheList.add(userMessage);

        params.put("messages",messageCacheList);
        for(MessageWrapper messageWrapper:messageCacheList){
            System.out.println(messageWrapper);
        }

        JSONObject result = ClientUtil.sendPostRequest(mingURL,params);

        System.out.println(result);
        if(result==null){
            return "错误:连接失败";
        }

        JSONArray choices = result.getJSONArray("choices");

        if(choices==null){

            return "choice == null"+result.toJSONString();
        }


        MessageWrapper responseMessage = new MessageWrapper();
        for(int i =0;i<choices.size();++i){
            JSONObject messageJson = choices.getJSONObject(i).getJSONObject("message");

            String role  = messageJson.getString("role");
            String content = messageJson.getString("content");

            responseMessage.setRole(role);
            responseMessage.setContent(content);
        }
        messageCacheList.add(responseMessage);
        saveUserChatHistory(userId,messageCacheList);

        String resultContent = responseMessage.getContent();
        if(checkIfNeedHospitalLocationInfo(queryMessage)){
            String userIP = IpUtil.getIpAddress(request);
            String mapInfo = MapUtil.getAddressByIP(userIP);
            resultContent += mapInfo;
        }
        return resultContent;
    }
    private boolean checkIfNeedHospitalLocationInfo(String content){
        String keyword = "医院";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(keyword);
        // 创建匹配器对象
        Matcher matcher = pattern.matcher(content);
        // 使用 find() 方法查找是否包含关键词
        boolean containsKeyword = matcher.find();

        if (containsKeyword) {
            return true;
        }
        else{
            return false;
        }
    }


    private void clearUserChatHistory(int userId){
        chatMessageMapper.clearChatMessageByUserId(userId);
    }
    private void saveUserChatHistory(Integer userId,List<MessageWrapper> messageList){
        for(MessageWrapper message: messageList){
            ChatMessage chatMessage = new ChatMessage(null, userId, message.getRole(), message.getContent());
            chatMessageMapper.insert(chatMessage);
        }

    }
    private void fetchUserChatHistory(int userId){
        List<ChatMessage> userChatHistory = chatMessageMapper.findChatMessageByUserId(userId);
//        List<MessageWrapper> messageCache = new ArrayList<>();
        if(userChatHistory==null){
            System.out.println("fetch chat history failed");
            return ;
        }
        for(ChatMessage chatMessage:userChatHistory){
            MessageWrapper messageWrapper = new MessageWrapper(chatMessage.getRole(),chatMessage.getContent());
            if(messageWrapper.getRole() !=null&& messageWrapper.getContent()!=null){
                messageCacheList.add(messageWrapper);
            }

        }
    }
}
