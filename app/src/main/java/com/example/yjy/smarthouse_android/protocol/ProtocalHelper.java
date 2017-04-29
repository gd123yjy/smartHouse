package com.example.yjy.smarthouse_android.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by yjy on 17-4-29.
 */
public class ProtocalHelper {

    public static HashMap<String,Integer> actions = new HashMap<>();
    public static HashMap<String,Integer> objects = new HashMap<>();
    public static HashMap<String,Integer> location = new HashMap<>();

    static {
        actions.put("开", ProtocalList.OPERATION_OPEN);
        actions.put("关", ProtocalList.OPERATION_CLOSE);

        objects.put("light_smartHome",ProtocalList.DEVICE_LIGHT);
        objects.put("webcam_smartHome",ProtocalList.DEVICE_CAMERA);
        objects.put("airControl_smartHome",ProtocalList.DEVICE_AIRCONTROL);

        location.put("门口",ProtocalList.LOCATION_UNDERDOOR);
        location.put("厨房",ProtocalList.LOCATION_KITCHEN);
        location.put("厕所",ProtocalList.LOCATION_BATHROOM);
        location.put("房间",ProtocalList.LOCATION_BEDROOM);
        location.put("卧室",ProtocalList.LOCATION_BEDROOM);
    }

    public synchronized static ProtocalParseResult parseCommand(String text) throws JSONException {

        //what the fucking encode
        JSONObject input = null;
        try {
            String decode = new String(text.getBytes(),"UTF-8");
            input = new JSONObject(decode);
        } catch (UnsupportedEncodingException e) {
            input = new JSONObject(text);
            e.printStackTrace();
        }
        JSONObject message = new JSONObject();
        Integer deviceID;

        //parse json message
        JSONObject slots = input.getJSONObject("semantic").getJSONObject("slots");

        Integer action = actions.get(actions.get(slots.getString("attrValue")));
        if (action == null){
            throw new NoActionException();
        }else {
            message.put("action",action);
        }

        //parse device id
        Integer deviceType;
        Integer s_location;
        try {
            deviceType = objects.get(input.getString("service"));
            s_location = location.get(slots.getJSONObject("location").getString("room"));
        } catch (JSONException e) {
            deviceType = null;
            s_location = null;
        }
        deviceID = DeviceHelper.parseDeviceID(deviceType,s_location);
        if (deviceID==null){
            throw new NoObjectException();
        }

        return new ProtocalParseResult(deviceID,message);
    }
}
