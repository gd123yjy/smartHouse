package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.model.dao.LightController;
import com.example.yjy.smarthouse_android.model.beans.Device;

import java.util.List;

/**
 * Created by mbc on 2017/6/4.
 */

public class NightSceneModel extends AbstractSceneModel {

    public NightSceneModel(List<Device> deviceList) {
        super(deviceList, SceneModelHelper.getAllScene()[1], SceneModelHelper.MODEL_ID_NIGHT);
        for (Device entr:
                getDeviceList()) {
            LightController.openLight(entr.getID());
        }
    }
}
