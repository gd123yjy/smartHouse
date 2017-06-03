package com.example.yjy.smarthouse_android.bussiness.device;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
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
 * 设备辅助类，维护一个HashMap进行设备缓存，缓存同步问题？
 */
public class DeviceHelper {

    //散列结构：type*1000+location*1=>device
    //维护一个HashMap进行设备缓存
    public static HashMap<Integer,Device> devices = new HashMap<>(20);

//    //维护一个HashMap存储灯光分组
//    public static HashMap<String,List<Device>> groupedLight = new HashMap<>();

    /**
     * 设备type * 1000 + location * 1作键
     * @param deviceType
     * @param s_location
     * @return
     */
    public static Integer parseDeviceID(Integer deviceType, Integer s_location) {
        if (deviceType==null) return null;
        if (s_location==null) s_location = ProtocolList.LOCATION_DEFAULT;
        return devices.get( deviceType * 1000 + s_location * 1 ).getID();
    }

    public static void parseDeviceList(RestfulResponse restfulResponse, List<Device> deviceList) throws ErrorCommandResponseException {
        if (restfulResponse == null) return;
        try {
            JSONObject jsonObject = restfulResponse.getJson();
            ProtocolCommandResponse response = ProtocolHelper.parseCommandResponse(jsonObject);
            JSONArray list = ( (JSONObject) response.getBody()).getJSONArray("device_list");
            for (int i=0;i<list.length();i++){
                JSONObject jsonDevice = list.getJSONObject(i);
                Device device = DeviceHelper.parseDevice(jsonDevice);
                //将解析得到的每一个Device, 添加到list,并做本地缓存
                deviceList.add(device);
                devices.put( device.getType() * 1000 + device.getPlace(),device);
            }
        } catch (ErrorResponseException e) {
            e.printStackTrace();
            throw new ErrorCommandResponseException();
        } catch (JSONException e) {
            throw new ErrorCommandResponseException();
        } catch (ErrorDeviceKeyException e) {
            throw new ErrorCommandResponseException();
        }
    }

    private static Device parseDevice(JSONObject jsonDevice) throws ErrorDeviceKeyException {
        Integer ID,location,type;
        try {
            ID = jsonDevice.getInt("ID");
            location = jsonDevice.getInt("location");
            type = jsonDevice.getInt("type");
        } catch (JSONException e) {
            throw new ErrorDeviceKeyException();
        }
        return new Device(ID,location,type);
    }
}
