package com.example.yjy.smarthouse_android.controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.bussiness.device.GroupHelper;
import com.example.yjy.smarthouse_android.bussiness.device.LightController;
import com.example.yjy.smarthouse_android.controller.adapters.GroupListViewAdapter;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.beans.DeviceGroup;
import com.example.yjy.smarthouse_android.model.dao.DeviceLister;
import com.example.yjy.smarthouse_android.toolkit.protocol.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbc on 2017/6/3.
 */
public class LightGroupFragment extends BaseControlFragment implements View.OnClickListener {

    private GroupListViewAdapter mGroupListAdapter;
    private Button add_group_btn;
    //存放所有“灯”设备,用于随机查找灯
    private List<Device> devices = new ArrayList<>();

    //存放灯光设备的分组信息
    ArrayList<DeviceGroup> groupList = new ArrayList<>();

    //存放所有“灯”设备名字，用于复选框文本展示
    String[] lightsName = null;
    //对应复选框的初始勾选状态
    boolean[] initStatus = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_light_group, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        //调用DeviceLister从HashMap缓存中取出当前所有的 “灯” 设备
        DeviceLister.getInstance().getLights(devices);

        final int lightsNum = devices.size();
        lightsName = new String[lightsNum];
        initStatus = new boolean[lightsNum];
        for(int i=0 ; i < lightsNum ; i++){
            Device device = devices.get(i);
            String lightName = StringHelper.location2String(device.getPlace()) + " "+ StringHelper.type2String(device.getType());
            lightsName[i]=lightName;
            initStatus[i]=false;
        }

        initListView();

        add_group_btn = (Button) this.getView().findViewById(R.id.add_group_btn);
        add_group_btn.setOnClickListener(this);
    }

    private void initListView() {
        ListView listView = (ListView) getView().findViewById(R.id.light_group_lv);
        // TODO: 2017/6/3  item layout is reused,to be replaced
        mGroupListAdapter = new GroupListViewAdapter(getActivity(), R.layout.item_group,groupList);
        listView.setAdapter(mGroupListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeviceGroup deviceGroup = (DeviceGroup) adapterView.getItemAtPosition(i);
                List<Device> deviceList = deviceGroup.getDeviceList();
                if (deviceGroup.getStatus()==DeviceGroup.STATUS_CLOSE){
                    for (Device entry :
                            deviceList) {
                        LightController.openLight(entry.getID());
                    }
                    deviceGroup.setStatus(DeviceGroup.STATUS_OPEN);
                    view.setBackgroundColor(Color.GRAY);
                }else {
                    for (Device entry :
                            deviceList) {
                        LightController.offLight(entry.getID());
                    }
                    deviceGroup.setStatus(DeviceGroup.STATUS_CLOSE);
                    view.setBackgroundColor(Color.WHITE);
                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        DeviceGroup group = GroupHelper.createGroup();
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity()).setTitle("请选择灯光");
        ad.setMultiChoiceItems(lightsName, initStatus, new DeviceSelectedListener(group));
        ad.setPositiveButton("我选好了", new DeviceSelectFinishedListener(group));
        ad.show();

    }

    private class DeviceSelectedListener implements DialogInterface.OnMultiChoiceClickListener {

        private DeviceGroup group;

        public DeviceSelectedListener(DeviceGroup group) {
            this.group = group;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
            if(isChecked){
                group.getDeviceList().add(devices.get(i));
            }else{
                group.getDeviceList().remove(devices.get(i));
            }
        }
    }

    private class GroupNamedListener implements DialogInterface.OnClickListener {

        private DeviceGroup group;
        private View dialogView;

        public GroupNamedListener(DeviceGroup group, View dialogView) {
            this.group = group;
            this.dialogView = dialogView;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            EditText editText = (EditText) dialogView.findViewById(R.id.edit_text);
            String groupName = editText.getText().toString();
            if(groupName == null || groupName.equals("")){
                Toast.makeText(getActivity(),"没有名字呢",Toast.LENGTH_SHORT).show();
            }else{
                group.setName(groupName);
                groupList.add(group);

                mGroupListAdapter.notifyDataSetChanged();
            }
        }
    }

    private class DeviceSelectFinishedListener implements DialogInterface.OnClickListener {

        private DeviceGroup group;

        public DeviceSelectFinishedListener(DeviceGroup group) {
            this.group = group;
        }

        // TODO: 2017/6/3 考虑全部不勾的情况
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getActivity());
            final View dialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_customize,null);
            customizeDialog.setView(dialogView);
            customizeDialog.setTitle("请输入分组名字");
            customizeDialog.setPositiveButton("写好啦", new GroupNamedListener(group,dialogView));
            //important!
            for (int ii=0;ii<initStatus.length;ii++){
                initStatus[ii] = false;
            }
            customizeDialog.show();
        }
    }
}
