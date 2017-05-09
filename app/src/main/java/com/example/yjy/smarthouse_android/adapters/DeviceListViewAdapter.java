package com.example.yjy.smarthouse_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yjy.smarthouse_android.R;

import java.util.List;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class DeviceListViewAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater mInflater;

    public DeviceListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_control, parent, false);
        ((TextView)convertView.findViewById(R.id.name_tv)).setText(getItem(position));
        return convertView;
    }

}
