package com.example.yjy.smarthouse_android.protocol;

/**
 * Created by yjy on 17-4-29.
 */
public class ProtocalList extends BaseProtocal{


    /** object types **/
    public static final int DEVICE_LIGHT = 2000;
    public static final int DEVICE_CAMERA = 2100;
    public static final int DEVICE_ELETRIC_MONITOR = 2200;
    public static final int DEVICE_AIRCONTROL = 2300;

    /** location **/
    public static final int LOCATION_UNDERDOOR = 3000;
    public static final int LOCATION_KITCHEN = 3001;
    public static final int LOCATION_BEDROOM = 3002;
    public static final int LOCATION_BATHROOM = 3003;

    public static final int LOCATION_DEFAULT = LOCATION_UNDERDOOR;



    /**
     * information such as groups,situations are stored in local android client
     */
}
