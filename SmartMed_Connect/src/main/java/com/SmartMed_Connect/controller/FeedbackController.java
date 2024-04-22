package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.Feedback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "feedback")
public class FeedbackController extends BaseController<Feedback> {

}
