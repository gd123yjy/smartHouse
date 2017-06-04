package com.example.yjy.smarthouse_android.bussiness.scene;

import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.dao.DeviceLister;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by mbc on 2017/6/4.
 */

public class SceneModelHelper {

    private static List<Device> lightList = DeviceLister.getInstance().getLights();
    private static AbstractSceneModel currentModel = new NoneSceneModel(lightList);

    private static String[] allScene = new String[]{"白天模式","夜间模式","睡眠模式"};

    //必须从0开始，以1为步长增加
    public final static int MODEL_ID_DAY = 0;
    public final static int MODEL_ID_NIGHT = 1;
    public final static int MODEL_ID_SLEEP = 2;

    public static String[] getAllScene(){
        return allScene;
    }

    public static AbstractSceneModel getCurrentModel() {
        return currentModel;
    }

    public static void setSceneModel(Class<? extends AbstractSceneModel> modelClass){
        try {
            currentModel = modelClass.getDeclaredConstructor(List.class).newInstance(lightList);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
