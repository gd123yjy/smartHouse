package com.example.yjy.smarthouse_android.protocol;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yjy on 17-4-29.
 */
public class RestfulRequest {
    // TODO: 17-4-29 restful client implemented by HTTPClient or OKHTTP
    public static RestfulRequest instance = new RestfulRequest();

    private RestfulRequest(){}

    public RestfulRequest buildHeader(Map.Entry<String,String> header) {
        return instance;
    }

    public RestfulRequest buildContent(Object message) {
        if (message instanceof JSONObject){

        }else {

        }
        return instance;
    }

    public static RestfulRequest create(String uri) {
        return instance;
    }

    public RestfulResponse send() {
        return new RestfulResponse();
    }
}
