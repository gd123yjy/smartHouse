package com.example.yjy.smarthouse_android.protocol;

/**
 * Created by yjy on 17-4-29.
 */
public class BaseProtocal {
    /**uniform format:
     *
     * operate object
     * {"action":"1"}
     * or
     * {"action":"1","attach":"xxx"}
     *
     **/

    /** actions **/
    //normal button
    public static final int OPERATION_CLOSE = 1000;
    public static final int OPERATION_OPEN = 1001;
    //camera
    public static final int SHOOT = 1100;
    public static final int PICK = 1101;
    //data collector
    public static final int START = 1200;
    public static final int STOP = 1201;
    public static final int UPLOAD = 1202;
    //air conditioner
    public static final int SET = 1300;
}
