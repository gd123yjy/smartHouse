package com.example.yjy.smarthouse_android.toolkit.protocol;

import android.text.TextUtils;

import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;

/**
 * Created by yjy on 17-5-12.
 */
public class StringHelper {
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

    public static String type2String(int type){
        switch (type){
            case ProtocolList.DEVICE_LIGHT:
                return "燈";
            case ProtocolList.DEVICE_AIRCONTROL:
                return "空調";
            case ProtocolList.DEVICE_CAMERA:
                return "相機";
            case ProtocolList.DEVICE_ELETRIC_MONITOR:
                return "監視器";
            default:
                return "其他類型";
        }
    }


    public static String location2String(int location){
        switch (location){
            case ProtocolList.LOCATION_BATHROOM:
                return "厠所";
            case ProtocolList.LOCATION_BEDROOM:
                return "臥室";
            case ProtocolList.LOCATION_KITCHEN:
                return "厨房";
            case ProtocolList.LOCATION_UNDERDOOR:
                return "門口";
            default:
                return "其他地方";
        }
    }
}
