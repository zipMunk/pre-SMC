package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.IllnessMedicine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("illness_medicine")
public class IllnessMedicineController extends BaseController<IllnessMedicine> {

}
