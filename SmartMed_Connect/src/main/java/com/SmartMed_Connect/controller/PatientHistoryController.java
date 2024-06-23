package com.SmartMed_Connect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PatientHistoryController 类是处理与历史记录相关的请求的控制器。
 * 继承自 BaseController，可以对 PatientHistoryController 类型的数据进行 CRUD 操作。
 */
@RestController//所有方法的返回值都直接写入 HTTP 响应体中，而不是返回视图。
@RequestMapping("patient_history")
public class PatientHistoryController {

}
