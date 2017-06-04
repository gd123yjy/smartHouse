package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.model.dao.LightController;
import com.example.yjy.smarthouse_android.model.beans.Device;

import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */

public class NoneSceneModel extends AbstractSceneModel {

    public NoneSceneModel(List<Device> deviceList) {
        super(deviceList,"一般模式",999);
        for (Device entr:
                getDeviceList()) {
            LightController.offLight(entr.getID());
        }
    }

}
