package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.bussiness.device.DeviceHelper;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.dao.LightController;

import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */

public class SleepSceneModel extends AbstractSceneModel {


    public SleepSceneModel(List<Device> deviceList) {
        super(deviceList, SceneModelHelper.getAllScene()[2] ,SceneModelHelper.MODEL_ID_SLEEP);
        int bedroomLightID = DeviceHelper.parseDeviceID(ProtocolList.DEVICE_LIGHT,ProtocolList.LOCATION_BEDROOM);
        for (Device entr:
                getDeviceList()) {
            if (entr.getID() == bedroomLightID){
                LightController.openLight(entr.getID());
            }else {
                LightController.offLight(entr.getID());
            }
        }
    }

}
