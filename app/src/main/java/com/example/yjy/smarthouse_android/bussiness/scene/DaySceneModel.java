package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.model.dao.LightController;
import com.example.yjy.smarthouse_android.model.beans.Device;

import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */

public class DaySceneModel extends AbstractSceneModel {

    public DaySceneModel(List<Device> deviceList) {
        super(deviceList, SceneModelHelper.getAllScene()[0], SceneModelHelper.MODEL_ID_DAY);
        for (Device entr:
                getDeviceList()) {
            LightController.offLight(entr.getID());
        }
    }

}
