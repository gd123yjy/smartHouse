package com.example.yjy.smarthouse_android.protocol;

import org.json.JSONObject;

/**
 * Created by yjy on 17-4-29.
 */
public class ProtocalParseResult {

    private Integer deviceID;
    private JSONObject jsonMessage;

    public ProtocalParseResult(Integer deviceID, JSONObject message) {
        this.deviceID = deviceID;
        this.jsonMessage = message;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public JSONObject getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(JSONObject jsonMessage) {
        this.jsonMessage = jsonMessage;
    }
}
