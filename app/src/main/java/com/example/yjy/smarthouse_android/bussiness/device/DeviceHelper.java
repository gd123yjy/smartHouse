package com.example.yjy.smarthouse_android.bussiness.device;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocalList;

import java.util.HashMap;

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
}
