package com.SmartMed_Connect.controller;


import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.utils.IpUtil;
import com.SmartMed_Connect.utils.MapUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/geo")
public class GeographyController {

    @GetMapping("/GeoInfo")
    public RespResult geoInfo(HttpServletRequest request){
        String userIP = IpUtil.getIpAddress(request);
        String result = MapUtil.getAddressByIP(userIP);
        return RespResult.success(result);
    }
}
