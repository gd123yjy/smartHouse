package com.example.yjy.smarthouse_android.protocol;

import android.text.TextUtils;
import android.util.Log;

import com.example.yjy.smarthouse_android.exception.NoActionException;
import com.example.yjy.smarthouse_android.exception.NoObjectException;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static String winString2Linux(final String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        final char[] chars = content.toCharArray();
        char curChar;
        for (int i =0 ; i < chars.length; i++) {
            curChar = chars[i];
            if ('\r' != curChar) {
                buffer.append(curChar);
            }
        }
        return buffer.toString();
    }

    public synchronized static ProtocalParseResult parseCommand(String text) throws JSONException {

        JSONObject input;

        input = new JSONObject(text);
        JSONObject message = new JSONObject();
        Integer deviceID;

        //parse json message
        JSONObject slots = input.getJSONObject("semantic").getJSONObject("slots");

        Integer action = actions.get(slots.getString("attrValue"));
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
