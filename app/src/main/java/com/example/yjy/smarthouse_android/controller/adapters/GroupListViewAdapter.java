package com.example.yjy.smarthouse_android.controller.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.beans.DeviceGroup;
import com.example.yjy.smarthouse_android.toolkit.protocol.StringHelper;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mbc on 2017/6/3.
 */

public class GroupListViewAdapter extends ArrayAdapter<DeviceGroup> {

    public GroupListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DeviceGroup> groupList) {
        super(context, resource, groupList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView != null){
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.group_name_tv);
            holder.detailTv = (TextView) convertView.findViewById(R.id.group_detail_tv);
            convertView.setTag(holder);
        }

        StringBuilder sb = new StringBuilder();
        DeviceGroup deviceGroup = getItem(position);

        holder.nameTv.setText(deviceGroup.getName());
        for(Device device:deviceGroup.getDeviceList()){
            sb.append(StringHelper.location2String(device.getPlace()) + StringHelper.type2String(device.getType()));
            sb.append(" ");
        }

        holder.detailTv.setText(sb.toString());

        return convertView;
    }

    class ViewHolder{
        TextView nameTv;
        TextView detailTv;
    }
}
