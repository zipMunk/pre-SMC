package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.Feedback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FeedbackController 是一个用于处理反馈相关请求的控制器类
 * 继承了 BaseController 泛型类，专门处理 Feedback 实体类的相关操作
 */
@RestController
@RequestMapping(value = "feedback")
public class FeedbackController extends BaseController<Feedback> {
// 由于继承了 BaseController<Feedback>，FeedbackController 会继承所有在 BaseController 中定义的通用方法
// 如保存、删除等，并且可以根据需要添加反馈特有的处理逻辑
}
