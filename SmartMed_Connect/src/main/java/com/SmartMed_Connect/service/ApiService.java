//package com.SmartMed_Connect.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.alibaba.dashscope.aigc.generation.Generation;
//import com.alibaba.dashscope.aigc.generation.GenerationParam;
//import com.alibaba.dashscope.aigc.generation.GenerationResult;
//import com.alibaba.dashscope.aigc.generation.GenerationOutput;
//import com.alibaba.dashscope.aigc.generation.models.QwenParam;
//import com.alibaba.dashscope.common.Message;
//import com.alibaba.dashscope.common.MessageManager;
//import com.alibaba.dashscope.common.Role;
//import com.alibaba.dashscope.utils.Constants;
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ApiService {
//    int count = 0;
//
//    public static GenerationParam createGenerationParam(List<Message> messages) {
//        return GenerationParam.builder()
//                .model("qwen-turbo")
//                .messages(messages)
//                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
//                .topP(0.8)
//                .build();
//    }
//
//    public static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
//        Generation gen = new Generation();
//        return gen.call(param);
//    }
//
//    @Value("${ai-key}")
//    private String apiKey;
//
//    List<Message> messages = new ArrayList<>();
//
//    public String query(String queryMessage) {
//        Constants.apiKey = apiKey;
//        try {
//            if (count == 0) {
//                messages.add(createMessage(Role.SYSTEM, "你是一名智能医生，请结合上下文，回答病人的问题，不许回答其他问题。"));
//            }
//            messages.add(createMessage(Role.USER, queryMessage));
//            GenerationParam param = createGenerationParam(messages);
//            GenerationResult result = callGenerationWithMessages(param);
//            messages.add(result.getOutput().getChoices().get(0).getMessage());
//            GenerationOutput output = result.getOutput();
//            Message message = output.getChoices().get(0).getMessage();
//            count++;
//            return message.getContent();
//            /*
//            Generation gen = new Generation();
//            MessageManager msgManager = new MessageManager(10);
//            Message systemMsg = Message.builder().role(Role.SYSTEM.getValue()).content("你是智能医生，你只回答与医疗相关的问题，不要回答其他问题！").build();
//            Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage).build();
//            msgManager.add(systemMsg);
//            msgManager.add(userMsg);
//            QwenParam param = QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get()).resultFormat(QwenParam.ResultFormat.MESSAGE).build();
//            GenerationResult result = gen.call(param);
//            GenerationOutput output = result.getOutput();
//            Message message = output.getChoices().get(0).getMessage();
//            return message.getContent();
//            */
//        } catch (Exception e) {
//            return "发生错误，请稍后再试。";
//        }
//    }
//
//    private static Message createMessage(Role role, String content) {
//        return Message.builder().role(role.getValue()).content(content).build();
//    }
//}


//package com.SmartMed_Connect.service;
//
//import com.alibaba.dashscope.aigc.generation.Generation;
//import com.alibaba.dashscope.aigc.generation.GenerationOutput;
//import com.alibaba.dashscope.aigc.generation.GenerationParam;
//import com.alibaba.dashscope.aigc.generation.GenerationResult;
//import com.alibaba.dashscope.aigc.generation.models.QwenParam;
//import com.alibaba.dashscope.common.Message;
//import com.alibaba.dashscope.common.MessageManager;
//import com.alibaba.dashscope.common.Role;
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import com.alibaba.dashscope.utils.Constants;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ApiService {
//
//    static {
//        // 设置 API key
//        com.alibaba.dashscope.utils.Constants.apiKey = "sk-d70a6efd593a4d72b91a1cecd4408411";
//    }
//    private final List<Message> messages = new ArrayList<>();
//    private int queryStep = 0;
//    private final PatientInfo patientInfo = new PatientInfo();
//    private boolean isTriggering=false;
//
//    public String query(String queryMessage) {
//        try {
//            String response = getNextQueryMessage();
//            savePatientInfo(queryMessage);
//
//            if (response == null) {
//                if (messages.isEmpty()) {
//                    messages.add(createMessage(Role.SYSTEM, "你是一名智能医生，请结合上下文，回答病人的问题，不许回答其他问题。"));
//                    messages.add(createMessage(Role.USER, patientInfo.toString()+"请你根据病人信息给出建议"));
//                }
//                else
//                {
//                    messages.add(createMessage(Role.USER, queryMessage));
//                }
//                GenerationParam param = createGenerationParam(messages);
//                GenerationResult result = callGenerationWithMessages(param);
//                messages.add(result.getOutput().getChoices().get(0).getMessage());
//                GenerationOutput output = result.getOutput();
//                System.out.println("开始吧");
//                printMessages();
//                return output.getChoices().get(0).getMessage().getContent();
//            }
//            return response;
//
//        } catch (Exception e) {
//            return "发生错误，请稍后再试。";
//        }
//    }
//
//    public void printMessages() {
//        for (Message message : messages) {
//            System.out.println("Role: " + message.getRole() + ", Content: " + message.getContent());
//        }
//    }
//
//    private String getNextQueryMessage() {
//            switch (queryStep) {
//                case 0:
//                    return "请提供您的个人基本信息，包括年龄、性别、身高和体重。";
//                case 1:
//                    return "您目前有哪些症状？请详细描述。";
//                case 2:
//                    return "请描述病情发作的相关细节，如发作时间、持续时间和发作频率。";
//                case 3:
//                    return "您的生活方式因素如何？包括饮食、运动、睡眠等方面。";
//                case 4:
//                    return "请描述您的病史，包括曾经患过的疾病、手术史等。";
//                case 5:
//                    return "您是否对某些药物过敏？如果有，请提供过敏药物的信息。";
//                case 6:
//                    return "您目前正在使用哪些药物？请提供正在使用的药物清单。";
//                default:
//                    return null;
//            }
//    }
//
//    private void savePatientInfo(String queryMessage) {
//        switch (queryStep) {
//            case 0:
//                patientInfo.setFirstQuery(queryMessage);// 第一次询问信息
//                break;
//            case 1:
//                patientInfo.setPersonalInfo(queryMessage);// 个人基本信息，如年龄、性别、身高、体重
//                break;
//            case 2:
//                patientInfo.setSymptoms(queryMessage);// 症状
//                break;
//            case 3:
//                patientInfo.setEpisodeDetails(queryMessage);// 病情发作相关细节，如发作时间，持续时间，发作频率
//                break;
//            case 4:
//                patientInfo.setLifestyleFactors(queryMessage);// 生活方式因素，包括饮食、运动、睡眠等
//                break;
//            case 5:
//                patientInfo.setMedicalHistory(queryMessage);// 病史
//                break;
//            case 6:
//                patientInfo.setAllergicDrugs(queryMessage);// 过敏药物
//                break;
//            case 7:
//                patientInfo.setUsingDrugs(queryMessage);// 正在使用的药物
//                break;
//        }
//        queryStep++;
//    }
//
//    private static GenerationParam createGenerationParam(List<Message> messages) {
//        return GenerationParam.builder()
//                .model("qwen-turbo")
//                .messages(messages)
//                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
//                .topP(0.8)
//                .build();
//    }
//
//    private static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
//        Generation gen = new Generation();
//        return gen.call(param);
//    }
//
//    private static Message createMessage(Role role, String content) {
//        return Message.builder().role(role.getValue()).content(content).build();
//    }
//
//    //通过调用大模型来判断用户是否正在咨询自身病情。
//    public  static boolean isMedicalQuery(String queryMessage) throws NoApiKeyException, InputRequiredException {
//        Generation gen = new Generation();
//        MessageManager msgManager = new MessageManager(10);
//        Message systemMsg =
//                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.下面的问题只能回答一个0~1之间的小数，其余内容都不用回答").build();
//        Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage+"，请你返回判断用户正在咨询自身病情的概率，值的范围是0~1，只需要返回一个小数就行").build();
//        msgManager.add(systemMsg);
//        msgManager.add(userMsg);
//        QwenParam param =
//                QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get())
//                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
//                        .build();
//        GenerationResult result = gen.call(param);
//        float probability= Float.parseFloat(result.getOutput().getChoices().get(0).getMessage().getContent());
//        System.out.println(probability);
//        return probability > 0.5;
//    }
//
//    private static class PatientInfo {
//        private String firstQuery;//第一次询问信息
//        private String personalInfo;//个人基本信息，如年龄、性别、身高、体重
//        private String symptoms;//症状
//        private String episodeDetails;//病情发作相关细节，如发作时间，持续时间，发作频率
//        private String lifestyleFactors;//表示生活方式因素，包括饮食、运动、睡眠等
//        private String medicalHistory;//病史
//        private String allergicDrugs;//过敏药物
//        private String usingDrugs;//正在使用的药物
//
//        @Override
//        public String toString() {
//            return "病人信息{" +
//                    "病人第一次询问为：'" + firstQuery + '\'' +//第一次询问信息
//                    ", 一般信息：'" + personalInfo + '\'' +//个人基本信息，如年龄、性别、身高、体重
//                    ", 症状：'" + symptoms + '\'' +//症状
//                    ", 发作细节：'" + episodeDetails + '\'' +//病情发作相关细节，如发作时间，持续时间，发作频率
//                    ", 生活方式：'" + lifestyleFactors + '\'' +//表示生活方式因素，包括饮食、运动、睡眠等
//                    ", 病史：'" + medicalHistory + '\'' +//病史
//                    ", 过敏药物：'" + allergicDrugs + '\'' +//过敏药物
//                    ", 正在使用的药物：'" + usingDrugs + '\'' +//正在使用的药物
//                    '}';
//        }
//
//        public void setLifestyleFactors(String lifestyleFactors) {
//            this.lifestyleFactors = lifestyleFactors;
//        }
//
//        public void setUsingDrugs(String usingDrugs) {
//            this.usingDrugs = usingDrugs;
//        }
//
//        public void setAllergicDrugs(String allergicDrugs) {
//            this.allergicDrugs = allergicDrugs;
//        }
//
//        public void setMedicalHistory(String medicalHistory) {
//            this.medicalHistory = medicalHistory;
//        }
//
//        public void setEpisodeDetails(String episodeDetails) {
//            this.episodeDetails = episodeDetails;
//        }
//
//        public void setSymptoms(String symptoms) {
//            this.symptoms = symptoms;
//        }
//
//        public void setPersonalInfo(String personalInfo) {
//            this.personalInfo = personalInfo;
//        }
//
//        public void setFirstQuery(String firstQuery) {
//            this.firstQuery = firstQuery;
//        }
//    }
//}



package com.SmartMed_Connect.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    static {
        // 设置 API key
        com.alibaba.dashscope.utils.Constants.apiKey = "sk-d70a6efd593a4d72b91a1cecd4408411";
    }
    private final List<Message> messages = new ArrayList<>();
    private int queryStep = 0;
    private PatientInfo patientInfo = new PatientInfo();
    private boolean isTriggering=false;

    public String query(String queryMessage) {
        try {
            if(messages.isEmpty())
            {
                messages.add(createMessage(Role.SYSTEM, "你是一名智能医生，请结合上下文，回答病人的问题，不许回答其他问题。"));
            }
            if(!isTriggering)//如果当前不在问诊模式
            {
                isTriggering=isMedicalQuery(queryMessage);//判断用户的问题是否触发问诊模式
                if(isTriggering)//如果用户的问题会触发问诊模式，调用触发问诊模式第一步询问
                {
                    System.out.println("触发问诊模式");
                    patientInfo= new PatientInfo();
                    savePatientInfo(queryMessage);
                    String response = getNextQueryMessage();
                    return response;
                }
                else//否则，使用通义千问的一般问答模式
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
                    isTriggering=false;
                    queryStep=0;
                    messages.add(createMessage(Role.USER, patientInfo.toString()+"请你根据病人信息给出建议"));
                    GenerationParam param = createGenerationParam(messages);
                    GenerationResult result = callGenerationWithMessages(param);
                    messages.add(result.getOutput().getChoices().get(0).getMessage());
                    GenerationOutput output = result.getOutput();
                    printMessages();
                    return output.getChoices().get(0).getMessage().getContent();
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
                return "请提供您的个人基本信息，包括年龄、性别、身高和体重。";
            case 2:
                return "您目前有哪些症状？请详细描述。";
            case 3:
                return "请描述病情发作的相关细节，如发作时间、持续时间和发作频率。";
            case 4:
                return "您的生活方式因素如何？包括饮食、运动、睡眠等方面。";
            case 5:
                return "请描述您的病史，包括曾经患过的疾病、手术史等。";
            case 6:
                return "您是否对某些药物过敏？如果有，请提供过敏药物的信息。";
            case 7:
                return "您目前正在使用哪些药物？请提供正在使用的药物清单。";
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
                patientInfo.setPersonalInfo(queryMessage);// 个人基本信息，如年龄、性别、身高、体重
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

    private static GenerationResult callGenerationWithMessages(GenerationParam param) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        return gen.call(param);
    }

    private static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }


    //通过调用大模型来判断用户是否正在咨询自身病情。
    public  static boolean isMedicalQuery(String queryMessage) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.下面的问题只能回答一个0~1之间的小数，其余内容都不用回答").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage+"，请你根据用户的询问给出用户此时身体不舒服的概率（0~1之间的小数），依据是出现疾病之类的词，只需要返回一个小数就行").build();
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
        private String personalInfo;//个人基本信息，如年龄、性别、身高、体重
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
}