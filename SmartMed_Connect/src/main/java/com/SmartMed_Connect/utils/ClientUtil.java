package com.SmartMed_Connect.utils;

import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.stereotype.Component;

import java.util.Map;

public class ClientUtil {


    public static JSONObject sendGetRequest(String url, Map<String,Object> params){
        HttpResponse jsonResponse = null;
        try {
            jsonResponse = Unirest.get(url).queryString(params)
                    .header("content-type", "application/json")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if(jsonResponse==null){
            return null;
        }
        return JSONObject.parseObject(jsonResponse.getBody().toString());

    }

    public static JSONObject sendPostRequest(String url, Map<String,Object> params){
        HttpResponse jsonResponse = null;
        try {
            jsonResponse = Unirest.post(url).header("content-type", "application/json")
                    .body(params)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if(jsonResponse==null||jsonResponse.getBody()==null){
            return null;
        }
        return JSONObject.parseObject(jsonResponse.getBody().toString());

    }
}
