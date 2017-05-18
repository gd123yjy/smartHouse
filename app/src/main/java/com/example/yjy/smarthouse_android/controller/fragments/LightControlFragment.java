package com.example.yjy.smarthouse_android.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.controller.adapters.DeviceListViewAdapter;
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

    //添加測試數據
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
        addTestData();

        lights_group_btn = (Button) getView().findViewById(R.id.gropu_btn);
        lights_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/5/18  
            }
        });
        ListView listView = (ListView) getView().findViewById(R.id.device_light_lv);
        DeviceLister.getInstance().getLights(lightList);
        adapter = new LightListViewAdapter(getActivity(), R.layout.item_light, lightList);
        listView.setAdapter(adapter);
    }
}
