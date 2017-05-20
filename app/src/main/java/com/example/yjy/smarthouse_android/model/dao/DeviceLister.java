package com.example.yjy.smarthouse_android.model.dao;

import com.example.yjy.smarthouse_android.bussiness.device.DeviceHelper;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.exceptions.ErrorCommandResponseException;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulRequest;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by yjy on 17-5-12.
 */
public class DeviceLister {

    private static final String host = "api.heclouds.com";
    private static final String app_key = "8T=kDEqacwFHyK79OCYz8HCB9yY=";
    private static DeviceLister instance = new DeviceLister();

    private DeviceLister(){
    }

    public static DeviceLister getInstance(){return instance;}

    public void refreshData(List<Device> deviceList) throws IllegalArgumentException{
        if (deviceList==null) throw new IllegalArgumentException();
        RestfulResponse commandStatus = requestDeviceList();
        //parse out the uuid and accordingly request for the device list
        String com_uuid = parseID(commandStatus.getJson());
        //check for command dealing status
        if (commandDealed(com_uuid)){
            RestfulResponse deviceListResponse = queryForCommandResult(com_uuid);
            try {
                DeviceHelper.parseDeviceList(deviceListResponse,deviceList);
            } catch (ErrorCommandResponseException e) {
                e.printStackTrace();
            }
        }

    }

    private static String parseID(JSONObject json) {
        try {
            return json.getJSONObject("data").getString("cmd_uuid");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private RestfulResponse queryForCommandResult(String com_uuid) {
        /**
         * 用于获取终端执行命令后的响应内容。
         HTTP方法	GET
         URL	http://<API_ADDRESS>/cmds/< cmd_uuid >/resp
         HTTP头部	api-key:xxxx-ffff-zzzzz， 必须master key
         HTTP body	用户自定义Json或二进制数据（小于64K）
         设备返回的命令执行响应
         响应的HTTP状态码对应的错误信息如下
         400	No api-key
         403	Auth Failed
         404	Not Found
         400	Invalid Parameter
         220	Command Created
         221	Command Sent
         520	Send Command Failed
         200	Command Response Received
         432	Command Response Timeout
         433	Command Response Too Large
         */
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds/"+ com_uuid+"/resp")
                .buildHeader("api-key",app_key)
                .buildOperation("GET")
                .send();
        return response;
    }

    private RestfulResponse requestDeviceList() {
        /**
         * 用于发送数据到登录设备云的设备。
         HTTP方法	POST
         URL	http://<API_ADDRESS>/cmds
         HTTP头部	api-key:xxxx-ffff-zzzzz，必须master key
         URL参数	device_id = //接收该数据的设备ID，必填。
         HTTP内容	用户自定义Json或二进制数据（小于64K）
         成功返回	{
         "errno": 0,
         "error":“succ”，
         "data":{
         //不超过64个字符字符串
         "cmd_uuid":“2302-312-FWs”
         }
         }
         */
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds?device_id="+ ProtocolList.ID_CONTROLLER+"&qos=1")
                .buildHeader("api-key",app_key)
                .buildContent(new ProtocolCommand(0, ProtocolList.LIST_DEVICE).getJsonMessage())
                .buildOperation("POST")
                .send();
        return response;
    }

    private boolean commandDealed(String com_uuid) {
        // TODO: 17-5-15 implemented if necessary
        /**
         * 用于查看命令发送状态
         HTTP方法	GET
         URL	http://<API_ADDRESS>/cmds/<cmd_uuid>
         HTTP头部	api-key:xxxx-ffff-zzzzz，必须master key
         成功返回	{
         "errno": 0,
         "error":“succ”，
         "data":{
         //0：设备不在线|Device not online
         1：命令已创建| Command Created
         2：命令已发往设备| Command Sent
         3：命令发往设备失败| Send Command Failed
         4：设备正常响应| Command Response Received
         5：命令执行超时| Command Response Timeout
         6：设备响应消息过长 | Command Response Too Large
         "status":1
         "desc":“sending”
         }
         }
         */
        /*
        while (unfinished){
            if (out of times) throws Exception
            query result
        }
        */
        try {
            synchronized (this){
                wait(500*10);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getLights(List<Device> lightList) {

    }
}
