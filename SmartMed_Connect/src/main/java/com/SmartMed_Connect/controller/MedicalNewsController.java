package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.MedicalNews;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * MedicalNewsController 类是处理医疗新闻相关请求的控制器
 */
@RestController
@RequestMapping("medical_news")
public class MedicalNewsController extends BaseController<MedicalNews> {


}
