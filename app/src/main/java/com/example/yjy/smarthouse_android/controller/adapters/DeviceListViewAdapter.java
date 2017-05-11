package com.example.yjy.smarthouse_android.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.model.dao.DeviceListSynctor;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class DeviceListViewAdapter extends ArrayAdapter<Device> {
    // TODO: 17-5-11 data sync to be done
    private Context mContext;
    private LayoutInflater mInflater;
    private DeviceListSynctor mSynctor;

    public DeviceListViewAdapter(Context context, int resource, DeviceListSynctor synctor) {
        super(context, resource);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mSynctor = synctor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView!=null){
            holder = (ViewHolder) convertView.getTag();
        }else{
            convertView = mInflater.inflate(R.layout.item_device, parent, false);
            holder = new ViewHolder();
            holder.idTv = (TextView) convertView.findViewById(R.id.device_id_tv);
            holder.placeTv = (TextView) convertView.findViewById(R.id.device_place_tv);
            holder.typeTv = (TextView) convertView.findViewById(R.id.device_type_tv);
            convertView.setTag(holder);
        }

        Device device = getItem(position);
        holder.idTv.setText(device.getID());
        holder.placeTv.setText(device.getPlace());
        holder.typeTv.setText(device.getType());
        return convertView;
    }


    class ViewHolder{
        TextView idTv, placeTv, typeTv;
    }

}
