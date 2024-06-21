package com.SmartMed_Connect.controller;

import cn.hutool.core.util.ObjectUtil;
import com.SmartMed_Connect.constant.MedicalConstants;
import com.SmartMed_Connect.entity.*;
import com.SmartMed_Connect.utils.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import com.SmartMed_Connect.entity.*;

import java.util.*;

@Controller
public class SystemController extends BaseController<User> {

    /**
     * 首页
     */
    @GetMapping("/index.html")
    public String index(Map<String, Object> map) {
        // 返回 "index" 字符串，表示将会渲染名为 "index" 的视图
        return "index";
    }

    /**
     * 智能医生
     */
    @GetMapping("/doctor")
    public String doctor(Map<String, Object> map) {
        // 如果未登录用户访问智能医生页面，则重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 返回 "doctor" 字符串，表示将会渲染名为 "doctor" 的视图
        return "doctor";
    }

    /**
     * 智能医生
     */
    @GetMapping("/smart_doctor")
    public String smart_doctor(Map<String, Object> map) {
        // 如果未登录用户访问智慧查询页面，则重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 返回 "doctor" 字符串，表示将会渲染名为 "smart_doctor" 的视图
        return "smart-doctor";
    }
    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        // 清空当前会话信息，实现用户退出登录
        session.invalidate();
        // 重定向到首页
        return "redirect:/index.html";
    }

    /**
     * 所有反馈
     */
    @GetMapping("/all-feedback")
    public String feedback(Map<String, Object> map) {
        // 如果未登录用户访问所有反馈页面，则重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 获取所有反馈列表
        List<Feedback> feedbackList = feedbackService.all();
        // 将反馈列表放入 map 中，传递给视图
        map.put("feedbackList", feedbackList);
        // 返回 "all-feedback" 字符串，表示将会渲染名为 "all-feedback" 的视图
        return "all-feedback";
    }

    /**
     * 我的资料
     */
    @GetMapping("/profile")
    public String profile(Map<String, Object> map) {
        // 如果未登录用户访问我的资料页面，则重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 返回 "profile" 字符串，表示将会渲染名为 "profile" 的视图
        return "profile";
    }

    /**
     * 查询相关疾病
     */
    @GetMapping("findIllness")
    public String findIllness(Map<String, Object> map, Integer kind, String illnessName, Integer page) {
        // 处理页码，默认为第一页
        page = ObjectUtils.isEmpty(page) ? 1 : page;
        // 调用 illnessService 的 findIllness 方法查询相关疾病信息
        Map<String, Object> illness = illnessService.findIllness(kind, illnessName, page);
        // 设置页面标题，根据查询条件动态生成
        if (Assert.notEmpty(kind)) {
            map.put("title", illnessKindService.get(kind).getName() + (illnessName == null ? "" : ('"' + illnessName + '"' + "的搜索结果")));
        } else {
            map.put("title", illnessName == null ? "全部" : ('"' + illnessName + '"' + "的搜索结果"));
        }
        // 如果登录用户不为空且查询条件中包含疾病类型，则记录操作历史
        if (loginUser != null && kind != null) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_OPERATE,
                    illnessKindService.get(kind).getId() + "," + (Assert.isEmpty(illnessName) ? "无" : illnessName));
        }
        // 如果登录用户不为空且查询条件中包含疾病名称，则记录操作历史
        if (loginUser != null && Assert.notEmpty(illnessName)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illnessName);
        }
        // 将查询结果和相关信息放入 map 中，传递给视图
        map.putAll(illness);
        map.put("page", page);
        map.put("kind", kind);
        map.put("illnessName", illnessName);
        map.put("kindList", illnessKindService.findList());
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId()));
        // 返回 "search-illness" 字符串，表示将会渲染名为 "search-illness" 的视图
        return "search-illness";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findIllnessOne")
    public String findIllnessOne(Map<String, Object> map, Integer id) {
        // 调用 illnessService 的 findIllnessOne 方法查询单个疾病信息
        Map<String, Object> illnessOne = illnessService.findIllnessOne(id);
        Illness illness = illnessService.get(id);
        // 如果登录用户不为空，则记录操作历史
        if (loginUser != null) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illness.getIllnessName());
        }
        // 将查询结果放入 map 中，传递给视图
        map.putAll(illnessOne);
        // 返回 "illness-reviews" 字符串，表示将会渲染名为 "illness-reviews" 的视图
        return "illness-reviews";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findMedicineOne")
    public String findMedicineOne(Map<String, Object> map, Integer id) {
        // 调用 medicineService 的 get 方法获取单个药品信息
        Medicine medicine = medicineService.get(id);
        // 将药品信息放入 map 中，传递给视图
        map.put("medicine", medicine);
        // 返回 "medicine" 字符串，表示将会渲染名为 "medicine" 的视图
        return "medicine";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findMedicines")
    public String findMedicines(Map<String, Object> map, String nameValue, Integer page) {
        // 处理页码，默认为第一页
        page = ObjectUtils.isEmpty(page) ? 1 : page;
        // 如果登录用户不为空且查询条件中包含药品名称，则记录操作历史
        if (loginUser != null && Assert.notEmpty(nameValue)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_MEDICINE, nameValue);
        }
        // 调用 medicineService 的 getMedicineList 方法查询相关药品信息
        map.putAll(medicineService.getMedicineList(nameValue, page));
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId()));
        map.put("title", nameValue);
        // 返回 "illness" 字符串，表示将会渲染名为 "illness" 的视图
        return "illness";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("globalSelect")
    public String globalSelect(Map<String, Object> map, String nameValue) {
        // 将中文逗号替换为英文逗号并按逗号分隔
        nameValue = nameValue.replace("，", ",");
        // 按逗号分割成列表
        List<String> idArr = Arrays.asList(nameValue.split(","));
        // 创建疾病集合
        Set<Illness> illnessSet = new HashSet<>();
        // 根据关键字查询疾病信息
        idArr.forEach(s -> {
            //在数据库中查找疾病名称（illness_name）中包含关键词 s 的疾病记录。
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_name", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            //在数据库中查找特殊症状（special_symptom）中包含关键词 s 的疾病记录。
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("special_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            //在数据库中查找疾病症状（illness_symptom）中包含关键词 s 的疾病记录。
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        map.put("illnessSet", illnessSet);
        return "index";
    }

    /**
     * 添加疾病页面
     */
    @GetMapping("add-illness")
    public String addIllness(Integer id, Map<String, Object> map) {
        // 如果 loginUser 为空，表示用户未登录，重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 创建一个新的 Illness 对象
        Illness illness = new Illness();
        // 如果 id 不为空且不为零，表示是编辑已有疾病信息
        if (Assert.notEmpty(id)) {
            // 根据 id 从数据库获取相应的疾病信息
            illness = illnessService.get(id);
        }
        // 获取所有疾病种类信息，并将其存入 illnessKinds 列表
        List<IllnessKind> illnessKinds = illnessKindService.all();
        // 将疾病信息和疾病种类信息存入 map，传递给前端页面
        map.put("illness", illness);
        map.put("kinds", illnessKinds);
        // 返回视图名称 "add-illness"，即对应的 Thymeleaf 模板文件名
        return "add-illness";
    }

    /**
     * 添加药品页面
     */
    @GetMapping("add-medical")
    public String addMedical(Integer id, Map<String, Object> map) {
        // 如果 loginUser 为空，表示用户未登录，重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 获取所有疾病信息，并将其存入 illnesses 列表
        List<Illness> illnesses = illnessService.all();
        // 创建一个新的 Medicine 对象
        Medicine medicine = new Medicine();
        // 如果 id 不为空且不为零，表示是编辑已有药品信息
        if (Assert.notEmpty(id)) {
            // 根据 id 从数据库获取相应的药品信息
            medicine = medicineService.get(id);
            // 遍历所有疾病，查询每个疾病与该药品的关联信息
            for (Illness illness : illnesses) {
                // 创建一个 IllnessMedicine 对象，并设置药品 ID 和疾病 ID
                List<IllnessMedicine> query = illnessMedicineService.query(IllnessMedicine.builder().medicineId(id).illnessId(illness.getId()).build());
                // 如果查询结果不为空，设置疾病的关联信息
                if (Assert.notEmpty(query)) {
                    illness.setIllnessMedicine(query.get(0));
                }
            }
        }
        // 将疾病信息和药品信息存入 map，传递给前端页面
        map.put("illnesses", illnesses);
        map.put("medicine", medicine);
        // 返回视图名称 "add-medical"，即对应的 Thymeleaf 模板文件名
        return "add-medical";
    }

    /**
     * 疾病管理页面
     */
    @GetMapping("all-illness")
    public String allIllness(Map<String, Object> map) {
        // 如果 loginUser 为空，表示用户未登录，重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 获取所有疾病信息，并将其存入 illnesses 列表
        List<Illness> illnesses = illnessService.all();
        // 遍历疾病列表，设置每个疾病的类型信息
        for (Illness illness : illnesses) {
            illness.setKind(illnessKindService.get(illness.getKindId()));
        }
        // 将疾病信息存入 map，传递给前端页面
        map.put("illnesses", illnesses);
        // 返回视图名称 "all-illness"，即对应的 Thymeleaf 模板文件名
        return "all-illness";
    }

    /**
     * 药品管理页面
     */
    @GetMapping("all-medical")
    public String allMedical(Map<String, Object> map) {
        // 如果 loginUser 为空，表示用户未登录，重定向到首页
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        // 获取所有药品信息，并将其存入 medicines 列表
        List<Medicine> medicines = medicineService.all();
        // 将药品信息存入 map，传递给前端页面
        map.put("medicines", medicines);
        // 返回视图名称 "all-medical"，即对应的 Thymeleaf 模板文件名
        return "all-medical";
    }
}
