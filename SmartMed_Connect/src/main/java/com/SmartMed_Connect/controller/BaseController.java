package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.IllnessKind;
import com.SmartMed_Connect.entity.User;
import com.SmartMed_Connect.service.*;
import com.SmartMed_Connect.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.SmartMed_Connect.component.EmailClient;
import com.SmartMed_Connect.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * BaseController 类为其他控制器类提供基础功能，包含常用的服务和处理请求、响应、会话的常用操作。
 *
 * @param <T> 泛型类型，用于支持不同的实体类型
 */
//@Controller
public class BaseController<T> {
    //    // 自动注入常用服务对象，提供业务逻辑处理
//    @Autowired
//    protected ApiService apiService;
    @Autowired
    protected UserService userService;

    @Autowired
    protected IllnessKindService illnessKindService;

    @Autowired
    protected IllnessMedicineService illnessMedicineService;

    @Autowired
    protected IllnessService illnessService;

    @Autowired
    protected MedicalNewsService medicalNewsService;

    @Autowired
    protected MedicineService medicineService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected FeedbackService feedbackService;

    @Autowired
    protected PatientHistoryService patientHistoryService;

    // 自动注入通用的基础服务对象，处理泛型类型的实体对象
    @Autowired
    protected BaseService<T> service;

    // 自动注入EmailClient对象，用于发送邮件
    @Autowired
    protected EmailClient emailClient;

    @Autowired
    protected SmartApiService smartApiService;

    // 定义用于处理请求、响应和会话的对象
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    public User loginUser;
    protected List<IllnessKind> kindList;


    /**
     * 保存、修改
     *
     * @param obj 目标对象
     * @return 响应结果
     */
    @ResponseBody//将控制器方法的返回值直接写入 HTTP 响应体中，而不是将其解析为一个视图名称。
    @PostMapping("save")//表示这个方法是用来处理 HTTP POST 请求的。POST 请求通常用于提交数据，比如表单数据或 JSON 数据，进行创建或更新操作。
    public RespResult save(T obj) {
        // 检查对象是否为空
        if (Assert.isEmpty(obj)) {
            return RespResult.fail("保存对象不能为空");
        }
        // 调用服务层的保存方法保存对象
        obj = service.save(obj);
        // 返回保存成功的响应结果
        return RespResult.success("保存成功", obj);
    }

    /**
     * 删除
     *
     * @param id 主键ID
     * @return 响应结果
     */
    @ResponseBody
    @PostMapping("/delete")
    public RespResult delete(Integer id) {
        // 检查ID是否为空
        if (Assert.isEmpty(id)) {
            return RespResult.fail("删除ID不能为空");
        }
        // 调用服务层的删除方法删除对象
        if (service.delete(id) == 0) {
            T t = service.get(id);
            // 检查对象是否存在
            if (Assert.isEmpty(t)) {
                return RespResult.notFound("数据不存在");
            }
            return RespResult.fail("删除失败");
        }
        // 返回删除成功的响应结果
        return RespResult.success("删除成功");
    }

    /**
     * 在每个子类方法调用之前先调用
     * @param request  HTTP 请求对象，包含请求的相关信息
     * @param response HTTP 响应对象，包含响应的相关信息
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        // 将传入的请求对象赋值给当前类的 request 属性，以便在其他方法中使用
        this.request = request;
        // 将传入的响应对象赋值给当前类的 response 属性，以便在其他方法中使用
        this.response = response;
        // 获取当前请求的会话对象，如果不存在则创建新的会话对象，并将其赋值给当前类的 session 属性
        this.session = request.getSession(true);
        // 从会话中获取存储的登录用户信息，并将其赋值给当前类的 loginUser 属性
        loginUser = (User) session.getAttribute("loginUser");
        // 从 IllnessKindService 中获取疾病分类列表，并将其存储在会话中，方便在其他地方使用
        session.setAttribute("kindList", illnessKindService.findList());
    }
}