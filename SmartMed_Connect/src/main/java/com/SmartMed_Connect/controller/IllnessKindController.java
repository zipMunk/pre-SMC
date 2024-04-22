package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.IllnessKind;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("illness_kind")
public class IllnessKindController extends BaseController<IllnessKind> {

}
