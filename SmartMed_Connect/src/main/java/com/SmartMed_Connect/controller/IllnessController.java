package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.Illness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("illness")
public class IllnessController extends BaseController<Illness> {

}
