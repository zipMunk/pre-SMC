package com.SmartMed_Connect.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import kong.unirest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {


    private static String amapKey="db5dc572b1fa4f88af5820889d0c2cbb";


    public static List<String> findHospitalPOI(String region){
        String url = "https://restapi.amap.com/v5/place/text";
        Map params = new HashMap<String,Object>();
        params.put("key",amapKey);
        //090100 综合医院
        //090200 专科医院
        //090300 诊所
        //090400 急救中心
        //090500 疾病预防机构
        //090600 医疗保健相关
        params.put("types","090100");
        params.put("region",region);
        JSONObject result = ClientUtil.sendGetRequest(url,params);
        Integer status = Integer.parseInt(result.getString("status"));
        String info = result.getString("info");

        List<String> addresses = new ArrayList<>();
        if(status==0){
            addresses.add("获取poi失败，info"+info);
            return addresses;
        }

        JSONArray pois = result.getJSONArray("pois");
        for(int i =0;i<pois.size();i++){
            JSONObject poi = pois.getJSONObject(i);
            String adname = poi.getString("adname");
            String name = poi.getString("name");
            String address = poi.getString("address");
            addresses.add("["+name+" 地址："+adname+address+"]");
        }

        return addresses;

    }
    public static String getAddressByIP(String userIP){
        String url = "https://restapi.amap.com/v3/ip";

        Map params = new HashMap<String,Object>();
        //本地部署时，利用postman进行测试只会显示127.0.0.1的ip，但是这里必须要用外网ip，否则的话可以直接省略，根据http请求来定位
        if(userIP.equalsIgnoreCase("127.0.0.1")==false&&!userIP.equalsIgnoreCase("localhost")==false){
            params.put("ip",userIP);
        }
        params.put("key",amapKey);

        JSONObject result = ClientUtil.sendGetRequest(url,params);
        Integer status = Integer.parseInt(result.getString("status"));
        String info = result.getString("info");
        if(status==0){
            return "获取位置信息失败,info:"+info+amapKey;
        }
        String province = result.getString("province");
        String city = result.getString("city");

        List<String> addresses = findHospitalPOI(city);

        StringBuilder sb = new StringBuilder(province);
        sb.append("<br>【根据您所在位置：");
        sb.append(city);
        sb.append("区域内的综合医院：<br>");
        if(addresses==null){
            return sb.toString();
        }
        for(String address:addresses){
            sb.append(address + "<br>");
        }
        sb.append("】");
        return sb.toString();
    }
}
