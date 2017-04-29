package com.example.yjy.smarthouse_android.protocol;

import java.util.HashMap;

/**
 * Created by yjy on 17-4-29.
 */
public class DeviceHelper {

    private static HashMap<Integer,HashMap<Integer,Integer>> deviceTable = new HashMap<>();

    static {
        HashMap<Integer,Integer> light = new HashMap<>();
        light.put(ProtocalList.LOCATION_BEDROOM,5645900);
        light.put(ProtocalList.LOCATION_UNDERDOOR,5645891);
        light.put(ProtocalList.LOCATION_KITCHEN,5529300);

        deviceTable.put(ProtocalList.DEVICE_LIGHT,light);
    }

    public static Integer parseDeviceID(Integer deviceType, Integer s_location) {
        return deviceTable.get(deviceType).get(s_location);
    }
}
