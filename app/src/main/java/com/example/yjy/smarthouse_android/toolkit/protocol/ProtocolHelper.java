package com.example.yjy.smarthouse_android.toolkit.protocol;

import com.example.yjy.smarthouse_android.bussiness.device.DeviceHelper;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommandResponse;
import com.example.yjy.smarthouse_android.exceptions.ErrorCommandException;
import com.example.yjy.smarthouse_android.exceptions.ErrorResponseException;
import com.example.yjy.smarthouse_android.exceptions.NoActionException;
import com.example.yjy.smarthouse_android.exceptions.NoObjectException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by yjy on 17-4-29.
 */
public class ProtocolHelper {

    public static HashMap<String,Integer> actions = new HashMap<>();
    public static HashMap<String,Integer> objects = new HashMap<>();
    public static HashMap<String,Integer> location = new HashMap<>();

    static {
        actions.put("开", ProtocolList.OPERATION_OPEN);
        actions.put("关", ProtocolList.OPERATION_CLOSE);

        objects.put("light_smartHome", ProtocolList.DEVICE_LIGHT);
        objects.put("webcam_smartHome", ProtocolList.DEVICE_CAMERA);
        objects.put("airControl_smartHome", ProtocolList.DEVICE_AIRCONTROL);

        location.put("门口", ProtocolList.LOCATION_UNDERDOOR);
        location.put("厨房", ProtocolList.LOCATION_KITCHEN);
        location.put("厕所", ProtocolList.LOCATION_BATHROOM);
        location.put("房间", ProtocolList.LOCATION_BEDROOM);
        location.put("卧室", ProtocolList.LOCATION_BEDROOM);
    }

    public synchronized static ProtocolCommand parseCommand(String text) throws ErrorCommandException {
        JSONObject input = null;
        try {
            input = new JSONObject(text);
        } catch (JSONException e) {
            throw new ErrorCommandException();
        }
        Integer deviceID;

        //parse json message
        JSONObject slots = null;
        try {
            slots = input.getJSONObject("semantic").getJSONObject("slots");
        } catch (JSONException e) {
            throw new ErrorCommandException();
        }

        Integer action = null;
        try {
            action = actions.get(slots.getString("attrValue"));
        } catch (JSONException e) {
            throw new ErrorCommandException();
        }
        if (action == null){
            throw new NoActionException();
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

        return new ProtocolCommand(deviceID,action);
    }


    public synchronized static ProtocolCommandResponse parseCommandResponse(JSONObject jsonObject) throws ErrorResponseException {
        try {
            return new ProtocolCommandResponse(jsonObject.getInt("action"),jsonObject.get("body"));
        } catch (JSONException e) {
            throw new ErrorResponseException();
        }
    }
}
