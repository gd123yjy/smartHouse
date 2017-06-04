package com.example.yjy.smarthouse_android.controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.bussiness.scene.DaySceneModel;
import com.example.yjy.smarthouse_android.bussiness.scene.NightSceneModel;
import com.example.yjy.smarthouse_android.bussiness.scene.NoneSceneModel;
import com.example.yjy.smarthouse_android.bussiness.scene.SceneModelHelper;
import com.example.yjy.smarthouse_android.bussiness.scene.SleepSceneModel;
import com.example.yjy.smarthouse_android.controller.adapters.LightListViewAdapter;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.dao.DeviceLister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class LightControlFragment extends BaseControlFragment {

    private LightListViewAdapter adapter;
    private List<Device> lightList = new ArrayList<>();

    private Button lights_group_btn;
    private Button lights_scene_btn;


    //添加测试数据
    private void addTestData(){
        for(int i=0;i<10;i++){
            Device device = new Device(1, ProtocolList.LOCATION_BATHROOM,ProtocolList.DEVICE_LIGHT);
            lightList.add(device);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_light, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
//        addTestData();

        lights_group_btn = (Button) getView().findViewById(R.id.gropu_btn);
        lights_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Fragment跳转
                if(mDlg != null){
                    mDlg.onReplace(view);
                }
            }
        });

        lights_scene_btn = (Button) getView().findViewById(R.id.scene_btn);
        lights_scene_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog ad =new AlertDialog.Builder(getActivity()).setTitle("选择情景模式")
                        .setSingleChoiceItems(SceneModelHelper.getAllScene() , SceneModelHelper.getCurrentModel().getModelID() , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case SceneModelHelper.MODEL_ID_DAY:
                                        SceneModelHelper.setSceneModel(DaySceneModel.class);
                                        break;
                                    case SceneModelHelper.MODEL_ID_NIGHT:
                                        SceneModelHelper.setSceneModel(NightSceneModel.class);
                                        break;
                                    case SceneModelHelper.MODEL_ID_SLEEP:
                                        SceneModelHelper.setSceneModel(SleepSceneModel.class);
                                        break;
                                    default:
                                        SceneModelHelper.setSceneModel(NoneSceneModel.class);
                                        break;
                                }
                                dialogInterface.dismiss();
                            }
                        }).create();
                ad.show();
            }
        });



        ListView listView = (ListView) getView().findViewById(R.id.device_light_lv);
        DeviceLister.getInstance().getLights(lightList);
        adapter = new LightListViewAdapter(getActivity(), R.layout.item_light, lightList);
        listView.setAdapter(adapter);
    }
}
