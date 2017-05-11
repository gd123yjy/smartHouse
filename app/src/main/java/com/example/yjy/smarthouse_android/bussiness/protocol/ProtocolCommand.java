package com.example.yjy.smarthouse_android.bussiness.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yjy on 17-4-29.
 */
public class ProtocolCommand {

    private Integer deviceID;
    private Integer action;

    public ProtocolCommand(Integer deviceID, Integer action) {
        this.deviceID = deviceID;
        this.action = action;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public JSONObject getJsonMessage(String attach ) {
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("action",action);
            jsonMessage.put("object",deviceID);
            if (attach!=null && ""!=attach){
                jsonMessage.put("attach",attach);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMessage;
    }

    public JSONObject getJsonMessage(){
        return getJsonMessage(null);
    }

}
