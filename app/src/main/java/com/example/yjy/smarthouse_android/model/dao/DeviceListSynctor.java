package com.example.yjy.smarthouse_android.model.dao;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocalList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulRequest;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.toolkit.protocol.ProtocolHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjy on 17-5-11.
 */
public class DeviceListSynctor {

    private List<Device> deviceList = new ArrayList<>();

    private final String host = "api.heclouds.com";
    private final String app_key = "8T=kDEqacwFHyK79OCYz8HCB9yY=";

    public DeviceListSynctor(){
        refleshData();
    }

    public List getDeviceList(){
        return deviceList;
    }

    public void refleshData(){
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds?device_id="+ ProtocalList.ID_CONTROLLER)
                .buildHeader("api-key",app_key)
                .buildContent(new ProtocolCommand(null,ProtocalList.LIST_DEVICE).getJsonMessage())
                .buildOperation("POST")
                .send();
// TODO: 17-5-11 parse out the uuid and accordingly request for the device list
        //String com_uuid = ProtocolHelper.parseResponse();
    }
}
