package com.example.yjy.smarthouse_android.controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;


import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.bussiness.device.LightController;
import com.example.yjy.smarthouse_android.model.beans.Device;
import com.example.yjy.smarthouse_android.toolkit.protocol.StringHelper;

import java.util.List;

/**
 * Created by mbc on 2017/5/18.
 */

public class LightListViewAdapter extends ArrayAdapter<Device> {

    private Context mContext;
    private LayoutInflater mInflater;

    public LightListViewAdapter(Context context, int resource, List list) {
        super(context, resource, list);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LightListViewAdapter.ViewHolder holder;
        if(convertView!=null){
            holder = (LightListViewAdapter.ViewHolder) convertView.getTag();
        }else{
            convertView = mInflater.inflate(R.layout.item_light, parent, false);
            holder = new LightListViewAdapter.ViewHolder();
            holder.concrete_btn = (Button) convertView.findViewById(R.id.concrete_btn);
            convertView.setTag(holder);
        }

        final Device device = getItem(position);
        String btn_text = StringHelper.location2String(device.getPlace()) + " "+ StringHelper.type2String(device.getType());
        holder.concrete_btn.setText(btn_text);
        holder.concrete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(device.getStatus()==0){
                    LightController.openLight(device.getID());
                    device.setStatus(1);
                    view.setBackgroundColor(Color.GRAY);

                }else{
                    LightController.offLight(device.getID());
                    device.setStatus(0);
                    view.setBackgroundColor(Color.WHITE);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        Button concrete_btn;
    }


}
