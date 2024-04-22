package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.History;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("history")
public class HistoryController extends BaseController<History> {

}
