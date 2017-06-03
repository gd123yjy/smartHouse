package com.example.yjy.smarthouse_android.model.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */

public class DeviceGroup {

    public final static int STATUS_OPEN = 1;
    public final static int STATUS_CLOSE = 0;

    private String name;
    private List<Device> deviceList;
    private int status;

    public DeviceGroup(){
        name="";
        deviceList = new ArrayList<>();
    }

    public DeviceGroup(String name,List deviceList){
        this.name = name;
        this.deviceList = deviceList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
