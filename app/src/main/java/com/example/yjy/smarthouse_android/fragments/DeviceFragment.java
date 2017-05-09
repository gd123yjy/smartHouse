package com.example.yjy.smarthouse_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.adapters.DeviceListViewAdapter;
import com.example.yjy.smarthouse_android.beans.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class DeviceFragment extends BaseFragment{

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
        ListView listView = (ListView) getView().findViewById(R.id.device_lv);
        List<Device> deviceList = new ArrayList<>();
        initDeviceList(deviceList);
        DeviceListViewAdapter adapter = new DeviceListViewAdapter(getActivity(), R.layout.item_device, deviceList);
        listView.setAdapter(adapter);
    }

    private void initDeviceList(List<Device> deviceList) {
        deviceList.add(new Device("dfsfds", "kitchen", "light"));
        deviceList.add(new Device("gfdsgs", "bedroom", "light"));
        deviceList.add(new Device("342224", "kitchen", "light"));
    }

}
