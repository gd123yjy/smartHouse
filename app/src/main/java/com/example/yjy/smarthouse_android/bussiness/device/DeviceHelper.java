package com.example.yjy.smarthouse_android.bussiness.device;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocalList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommandResponse;
import com.example.yjy.smarthouse_android.exceptions.ErrorCommandResponseException;
import com.example.yjy.smarthouse_android.exceptions.ErrorDeviceKeyException;
import com.example.yjy.smarthouse_android.exceptions.ErrorResponseException;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;
import com.example.yjy.smarthouse_android.toolkit.protocol.ProtocolHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yjy on 17-4-29.
 */
public class DeviceHelper {
    // TODO: 17-5-1 device id to be modified

    private static HashMap<Integer,HashMap<Integer,Integer>> deviceTable = new HashMap<>();

    static {
        HashMap<Integer,Integer> light = new HashMap<>();
        light.put(ProtocalList.LOCATION_BATHROOM,5696229);
        light.put(ProtocalList.LOCATION_BEDROOM,5645900);
        light.put(ProtocalList.LOCATION_KITCHEN,5529300);
        light.put(ProtocalList.LOCATION_UNDERDOOR,5645891);

        HashMap<Integer,Integer> camera = new HashMap<>();
        camera.put(ProtocalList.LOCATION_BATHROOM,5696229);
        camera.put(ProtocalList.LOCATION_BEDROOM,5645900);
        camera.put(ProtocalList.LOCATION_KITCHEN,5529300);
        camera.put(ProtocalList.LOCATION_UNDERDOOR,5645891);

        HashMap<Integer,Integer> airControl = new HashMap<>();
        airControl.put(ProtocalList.LOCATION_BATHROOM,5696229);
        airControl.put(ProtocalList.LOCATION_BEDROOM,5645900);
        airControl.put(ProtocalList.LOCATION_KITCHEN,5529300);
        airControl.put(ProtocalList.LOCATION_UNDERDOOR,5645891);

        deviceTable.put(ProtocalList.DEVICE_LIGHT,light);
        deviceTable.put(ProtocalList.DEVICE_CAMERA,camera);
        deviceTable.put(ProtocalList.DEVICE_AIRCONTROL,airControl);
    }

    public static Integer parseDeviceID(Integer deviceType, Integer s_location) {
        if (deviceType==null) return null;
        if (s_location==null) s_location = ProtocalList.LOCATION_DEFAULT;
        return deviceTable.get(deviceType).get(s_location);
    }

    public static void parseDeviceList(RestfulResponse deviceListResponse, List<Device> deviceList) throws ErrorCommandResponseException {
        if (deviceListResponse == null) return;
        try {
            JSONObject jsonObject = deviceListResponse.getJson();
            ProtocolCommandResponse response = ProtocolHelper.parseCommandResponse(jsonObject);
            JSONArray list = ( (JSONObject) response.getBody()).getJSONArray("device_list");
            for (int i=0;i<list.length();i++){
                JSONObject jsonDevice = list.getJSONObject(i);
                deviceList.add(DeviceHelper.parseDevice(jsonDevice));
            }
        } catch (ErrorResponseException e) {
            throw new ErrorCommandResponseException();
        } catch (JSONException e) {
            throw new ErrorCommandResponseException();
        } catch (ErrorDeviceKeyException e) {
            throw new ErrorCommandResponseException();
        }
    }

    private static Device parseDevice(JSONObject jsonDevice) throws ErrorDeviceKeyException {
        String ID,location,type;
        try {
            ID = jsonDevice.getString("ID");
            location = jsonDevice.getString("location");
            type = jsonDevice.getString("type");
        } catch (JSONException e) {
            throw new ErrorDeviceKeyException();
        }
        return new Device(ID,location,type);
    }
}
