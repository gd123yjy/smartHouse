package com.example.yjy.smarthouse_android.model.dao;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulRequest;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;

/**
 * Created by mbc on 2017/5/19.
 */

/**
 * 负责灯光控制，目前实现开灯，关灯
 */
public class LightController {

    private static final String host = "api.heclouds.com";
    private static final String app_key = "8T=kDEqacwFHyK79OCYz8HCB9yY=";

    public static RestfulResponse controlLight(Integer device_id, Integer action){
        RestfulResponse response =  RestfulRequest.create()
                .buildUri("http://"+host+"/cmds?device_id="+ ProtocolList.ID_CONTROLLER+"&qos=1")
                .buildHeader("api-key",app_key)
                .buildContent(new ProtocolCommand(device_id, action).getJsonMessage())
                .buildOperation("POST")
                .send();
        return response;
    }

    public static RestfulResponse openLight(Integer device_id){
        return controlLight(device_id,ProtocolList.OPERATION_OPEN);
    }

    public static RestfulResponse offLight(Integer device_id){
        return controlLight(device_id,ProtocolList.OPERATION_CLOSE);
    }
}
