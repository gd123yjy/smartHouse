package com.example.yjy.smarthouse_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.adapters.ControllListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class ControlFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_controll, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.controll_lv);
        List<String> nameList = new ArrayList<>();

        nameList.add("灯光控制");
        nameList.add("水电监控");
        nameList.add("门禁管理");
        nameList.add("温湿管理");
        listView.setAdapter(new ControllListViewAdapter(getActivity(), R.layout.item_controll, nameList));
    }


}
