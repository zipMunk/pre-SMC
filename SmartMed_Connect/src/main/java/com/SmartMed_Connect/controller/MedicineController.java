package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.entity.Medicine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicine")
public class MedicineController extends BaseController<Medicine> {

}
