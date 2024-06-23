package com.SmartMed_Connect.service;

import com.SmartMed_Connect.controller.UserController;
import com.SmartMed_Connect.entity.PatientHistory;
import com.SmartMed_Connect.entity.User;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {


    @Autowired
    protected PatientHistoryService patientHistoryService;

    @Autowired
    private UserController userController;

    @Autowired
    protected UserService userService;

    //病史实体类
    private PatientHistory patientHistory = new PatientHistory();

    static {
        // 设置 API key
        com.alibaba.dashscope.utils.Constants.apiKey = "sk-d70a6efd593a4d72b91a1cecd4408411";
    }
    // 消息列表
    private final List<Message> messages = new ArrayList<>();
    //智能问诊模式到了第几步
    private int queryStep = 0;
    //用于临时存储智能问诊模式得到的信息
    private PatientInfo patientInfo = new PatientInfo();
    //是否正在触发智能问诊模式
    private boolean isTriggering=false;


    //处理用户与 Qwen 对话的函数
    public String query(String queryMessage) {
        try {
            // 初始消息为空时，添加系统消息
            if(messages.isEmpty())
            {
                messages.add(createMessage(Role.SYSTEM, "你是一名智能医生，请结合上下文，回答病人的问题，不许回答其他问题。"));
//                System.out.println("用户Id"+userController.loginUser.getId());
//                patientHistoryService.findByUserId(userController.loginUser.getId());
            }
            //如果当前不在问诊模式
            if(!isTriggering)
            {
                isTriggering=isMedicalQuery(queryMessage); //判断用户的问题是否触发问诊模式
                //如果用户的问题会触发问诊模式，调用触发问诊模式第一步询问
                if(isTriggering)
                {
                    System.out.println("触发问诊模式");
                    patientInfo= new PatientInfo();
                    savePatientInfo(queryMessage);
                    String response=getNextQueryMessage();
                    return response;
                }
                //否则，使用通义千问的一般问答模式
                else
                {
                    messages.add(createMessage(Role.USER, queryMessage));
                    GenerationParam param = createGenerationParam(messages);
                    GenerationResult result = callGenerationWithMessages(param);
                    messages.add(result.getOutput().getChoices().get(0).getMessage());
                    GenerationOutput output = result.getOutput();
                    return output.getChoices().get(0).getMessage().getContent();
                }
            }
            else //已经进入问诊模式了
            {
                if(queryStep==7)
                {
                    savePatientInfo(queryMessage);//保存用户最后一次回答
                    isTriggering=false;//重置触发标志
                    queryStep=0;//重置问诊步骤



                    //查找病史
                    List<PatientHistory> PatientHistoryList = patientHistoryService.findByUserId(userController.loginUser.getId());
                    // 将病史列表中的元素转换成字符串并连接起来
                    String historyString = PatientHistoryList.stream()
                            .map(PatientHistory::toString)
                            .collect(Collectors.joining("。      "));
                    //打印查看病史列表
                    System.out.println(historyString);
                    patientInfo.toString();
                    //整合病史和当前病情
                    messages.add(createMessage(Role.USER,"我的的以往病史是："+historyString+
                            "            " +
                            "我的当前的病症是："+patientInfo.toString()+
                            "            " +
                            "请你针对我的的当前症状给出相应的建议，如果当前病症和我的病史相关那就需要考虑一下病史"));
                    GenerationParam param = createGenerationParam(messages);
                    GenerationResult result = callGenerationWithMessages(param);
                    messages.add(result.getOutput().getChoices().get(0).getMessage());
                    GenerationOutput output = result.getOutput();

//                    printMessages();
//                    System.out.println(output.getChoices().get(0).getMessage().getContent());
                    patientHistory=PatientInfoToPatientHistory(patientInfo,output.getChoices().get(0).getMessage().getContent());
                    //把当前的病症保存为病史
                    patientHistoryService.save(patientHistory);
//

                    return "考虑到您的病史和当前症状得到的诊断结果是：                   "+output.getChoices().get(0).getMessage().getContent();
                }
                savePatientInfo(queryMessage);//保存上一次提问的用户回答
                String response = getNextQueryMessage();//获得AI下一次的问题
                return response;
            }
        } catch (Exception e) {
            return "发生错误，请稍后再试。";
        }
    }

    public void printMessages() {
        for (Message message : messages) {
            System.out.println("Role: " + message.getRole() + ", Content: " + message.getContent());
        }
    }

    private String getNextQueryMessage() {
        queryStep++;
        switch (queryStep) {
            case 1:
                return "提供一下你最近的身高和体重信息";
            case 2:
                return "你目前有哪些症状？能详细和我说一下";
            case 3:
                return "病情发作的细节你有留意吗？，比如发作时间、持续时间和发作频率。";
            case 4:
                return "你的平时生活习惯怎样？比如饮食、运动、睡眠等方面。";
            case 5:
                return "请大致描述一下你的病史，包括曾经患过的疾病、手术史等。";
            case 6:
                return "你是否对某些药物过敏？如果有，请说一下你过敏的药物信息";
            case 7:
                return "你目前有那些正在使用的药物？请提供正在使用的药物清单。";
            default:
                break;
        }
        return null;
    }

    private void savePatientInfo(String queryMessage) {
        switch (queryStep) {
            case 0:
                patientInfo.setFirstQuery(queryMessage);// 第一次询问信息
                break;
            case 1:
                patientInfo.setPersonalInfo(queryMessage);// 个人基本信息，身高、体重
                break;
            case 2:
                patientInfo.setSymptoms(queryMessage);// 症状
                break;
            case 3:
                patientInfo.setEpisodeDetails(queryMessage);// 病情发作相关细节，如发作时间，持续时间，发作频率
                break;
            case 4:
                patientInfo.setLifestyleFactors(queryMessage);// 生活方式因素，包括饮食、运动、睡眠等
                break;
            case 5:
                patientInfo.setMedicalHistory(queryMessage);// 病史
                break;
            case 6:
                patientInfo.setAllergicDrugs(queryMessage);// 过敏药物
                break;
            case 7:
                queryStep++;
                patientInfo.setUsingDrugs(queryMessage);// 正在使用的药物
                break;
        }
    }

    private static GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .build();
    }

    private static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException
    {
        Generation gen = new Generation();
        return gen.call(param);
    }

    private static Message createMessage(Role role, String content) 
    {
        return Message.builder().role(role.getValue()).content(content).build();
    }


    //通过调用大模型来判断用户是否正在咨询自身病情。
    public  static boolean isMedicalQuery(String queryMessage) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.下面的问题只能回答一个0~1之间的小数，其余内容都不用回答").build();
        Message userMsg =
                Message.builder().role(Role.USER.getValue()).content(queryMessage+"，请你根据用户的询问给出用户此时身体不舒服的概率（0~1之间的小数），依据是出现疾病之类的词，只需要返回一个小数就行").build();
        msgManager.add(systemMsg);
        msgManager.add(userMsg);
        QwenParam param =
                QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .build();
        GenerationResult result = gen.call(param);
        System.out.println("获取概率内容"+result.getOutput().getChoices().get(0).getMessage().getContent());
        float probability= Float.parseFloat(result.getOutput().getChoices().get(0).getMessage().getContent());
        System.out.println("概率"+probability);
        return probability > 0.84;
    }

    private static class PatientInfo {
        private String firstQuery;//第一次询问信息
        private String personalInfo;//个人基本信息，如身高、体重
        private String symptoms;//症状
        private String episodeDetails;//病情发作相关细节，如发作时间，持续时间，发作频率
        private String lifestyleFactors;//表示生活方式因素，包括饮食、运动、睡眠等
        private String medicalHistory;//病史
        private String allergicDrugs;//过敏药物
        private String usingDrugs;//正在使用的药物

        @Override
        public String toString() {
            return "病人信息{" +
                    "病人第一次询问为：'" + firstQuery + '\'' +//第一次询问信息
                    ", 一般信息：'" + personalInfo + '\'' +//个人基本信息，如年龄、性别、身高、体重
                    ", 症状：'" + symptoms + '\'' +//症状
                    ", 发作细节：'" + episodeDetails + '\'' +//病情发作相关细节，如发作时间，持续时间，发作频率
                    ", 生活方式：'" + lifestyleFactors + '\'' +//表示生活方式因素，包括饮食、运动、睡眠等
                    ", 病史：'" + medicalHistory + '\'' +//病史
                    ", 过敏药物：'" + allergicDrugs + '\'' +//过敏药物
                    ", 正在使用的药物：'" + usingDrugs + '\'' +//正在使用的药物
                    '}';
        }

        public void setLifestyleFactors(String lifestyleFactors) {
            this.lifestyleFactors = lifestyleFactors;
        }

        public void setUsingDrugs(String usingDrugs) {
            this.usingDrugs = usingDrugs;
        }

        public void setAllergicDrugs(String allergicDrugs) {
            this.allergicDrugs = allergicDrugs;
        }

        public void setMedicalHistory(String medicalHistory) {
            this.medicalHistory = medicalHistory;
        }

        public void setEpisodeDetails(String episodeDetails) {
            this.episodeDetails = episodeDetails;
        }

        public void setSymptoms(String symptoms) {
            this.symptoms = symptoms;
        }

        public void setPersonalInfo(String personalInfo) {
            this.personalInfo = personalInfo;
        }

        public void setFirstQuery(String firstQuery) {
            this.firstQuery = firstQuery;
        }
    }

    private PatientHistory PatientInfoToPatientHistory(PatientInfo patientInfo,String currentDiagnosticResult) {
        System.out.println("执行到这里了");
        patientHistory.setId(null);
        //设置用户id
        patientHistory.setUserId(userController.loginUser.getId());

        //设置用户第一次询问的信息
        patientHistory.setFirstQuery(patientInfo.firstQuery);
        //设置用户年龄
        patientHistory.setUserAge(userController.loginUser.getUserAge());
        //设置用户性别
        patientHistory.setUserSex(userController.loginUser.getUserSex());
        //设置用户身高体重
        patientHistory.setHeightAndWeight(patientInfo.personalInfo);
        //设置用户症状
        patientHistory.setSymptoms(patientInfo.symptoms);
        //设置用户病情发作相关细节，如发作时间，持续时间，发作频率
        patientHistory.setEpisodeDetails(patientInfo.episodeDetails);
        //表示生活方式因素，包括饮食、运动、睡眠等
        patientHistory.setLifestyleFactors(patientInfo.lifestyleFactors);
        //病史
        patientHistory.setMedicalHistory(patientInfo.medicalHistory);
        //过敏药物
        patientHistory.setAllergicDrugs(patientInfo.allergicDrugs);
        //正在使用的药物
        patientHistory.setUsingDrugs(patientInfo.usingDrugs);
        //当前诊断结果
        patientHistory.setDiagnosticResult(currentDiagnosticResult);
        //查看保存内容
        System.out.println(patientHistory);
        return patientHistory;
    }
}