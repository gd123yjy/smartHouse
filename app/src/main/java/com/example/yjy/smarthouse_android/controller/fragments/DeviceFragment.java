package com.example.yjy.smarthouse_android.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.controller.adapters.DeviceListViewAdapter;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.dao.DeviceLister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class DeviceFragment extends BaseFragment{

    private DeviceListViewAdapter adapter;
    private List<Device> deviceList = new ArrayList<>();


    //添加測試數據
    private void addTestData(){
        for(int i=0;i<10;i++){
            Device device = new Device(1, ProtocolList.LOCATION_BATHROOM,ProtocolList.DEVICE_LIGHT);
            deviceList.add(device);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_control, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initView();
    }

    private void initView() {
//        addTestData();

        ListView listView = (ListView) getView().findViewById(R.id.device_lv);
        DeviceLister.getInstance().refreshData(deviceList);
        adapter = new DeviceListViewAdapter(getActivity(), R.layout.item_device,deviceList);
        listView.setAdapter(adapter);
    }


}
