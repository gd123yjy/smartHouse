package com.example.yjy.smarthouse_android.model.dao;

import com.example.yjy.smarthouse_android.bussiness.device.DeviceHelper;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.exceptions.ErrorCommandResponseException;
import com.example.yjy.smarthouse_android.exceptions.QueryOutOfTimeException;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulRequest;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yjy on 17-5-12.
 */

/**
 * 2017/5/29
 *
 */
public class DeviceLister {

    private static final String host = "api.heclouds.com";
    private static final String app_key = "8T=kDEqacwFHyK79OCYz8HCB9yY=";
    private static DeviceLister instance = new DeviceLister();

    private DeviceLister(){}

    public static DeviceLister getInstance(){return instance;}

    /**
     * 更新设备列表，通过向OneNet发送命令包
     * @param deviceList 用来存放所有来自OneNet所响应设备的list
     * @throws IllegalArgumentException
     * @throws QueryOutOfTimeException
     */
    public void refreshData(List<Device> deviceList) throws IllegalArgumentException, QueryOutOfTimeException {
        if (deviceList==null) throw new IllegalArgumentException();
        RestfulResponse response ;
        response = this.sendRequest();
        String cmd_uuid = this.parseCommandId(response);

        //check for command dealing status
        boolean isFinished = false;
        boolean outOfTime = false;
        //200ms进行一次查询，直至出错或者命令已响应
        long startTime = System.currentTimeMillis();
        do{
            try{
                Thread.sleep(100*2);
            } catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
            outOfTime = (System.currentTimeMillis()-startTime)>3*1000;
        }while(!(isFinished = isResponseReceived(cmd_uuid)) && !outOfTime);

        if(isFinished){
            //命令响应成功
           RestfulResponse deviceResponse = this.getCommandResponse(cmd_uuid);
            try {
                DeviceHelper.parseDeviceList(deviceResponse,deviceList);
            } catch (ErrorCommandResponseException e) {
                //control回包错误
                e.printStackTrace();
            }
        }else if (outOfTime){
            //超时
            throw new QueryOutOfTimeException();
        }

    }

    /**
     *  向OneNet设备云发送命令
     * @return
     */
    private RestfulResponse sendRequest() {
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds?device_id="+ ProtocolList.ID_CONTROLLER+"&qos=1")
                .buildHeader("api-key",app_key)
                .buildContent(new ProtocolCommand(0, ProtocolList.LIST_DEVICE).getJsonMessage())
                .buildOperation("POST")
                .send();
        return response;
    }

    /**
     *  查询命令状态
     * @param cmd_uuid
     * @return
     */
    private RestfulResponse getCommandStatus(String cmd_uuid) {
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds/"+cmd_uuid)
                .buildHeader("api-key",app_key)
                .buildOperation("GET")
                .send();
        return response;

    }

    /**
     * 取得命令响应内容
     * @param cmd_uuid
     * @return
     */
    private RestfulResponse getCommandResponse(String cmd_uuid) {
        RestfulResponse response = RestfulRequest.create()
                .buildUri("http://"+host+"/cmds/"+ cmd_uuid +"/resp")
                .buildHeader("api-key",app_key)
                .buildOperation("GET")
                .send();
        return response;
    }


    /**
     * 根据OneNet的响应解析出 command id
     * @param response
     * @return
     */
    private String parseCommandId(RestfulResponse response) {
        try {
            return response.getJson().getJSONObject("data").getString("cmd_uuid");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断指定命令是否已经得到终端设备的响应( 4：设备正常响应| Command Response Received)
     * @param cmd_uuid 命令ID
     * @return
     */
    private boolean isResponseReceived(String cmd_uuid){
        try{
            RestfulResponse response = this.getCommandStatus(cmd_uuid);
            int status = response.getJson().getJSONObject("data").getInt("status");
            return status==4;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 从DeviceHelper缓存中取出所有“灯”设备
     * @param lightList
     */
    public void getLights(List<Device> lightList) {
        if(lightList!=null && lightList.size()>0)
            return;
        HashMap<Integer,Device> devices = DeviceHelper.devices;
        if(devices!=null && devices.size()>0){
            for(Map.Entry<Integer,Device> entry: devices.entrySet()){
                if(entry.getValue().getType()==2000){
                    lightList.add(entry.getValue());
                }
            }
        }
    }

    /**
     * 从DeviceHelper缓存中取出所有“灯”设备
     * @return  lightList
     */
    public List<Device> getLights() {
        List<Device> lightList = new ArrayList<>();
        this.getLights(lightList);
        return lightList;
    }
}
