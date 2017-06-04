package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.model.beans.Device;

import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */

public abstract class AbstractSceneModel {

    private List<Device> deviceList;
    private String modelName;
    private int modelID;

    public AbstractSceneModel(List<Device> deviceList,String modelName,int modelID) {
        this.deviceList = deviceList;
        this.modelName = modelName;
        this.modelID = modelID;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public String getModelName(){
        return modelName;
    }

    public int getModelID(){
        return modelID;
    }
}
