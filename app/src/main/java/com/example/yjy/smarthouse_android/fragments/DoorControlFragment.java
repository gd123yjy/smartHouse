package com.example.yjy.smarthouse_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjy.smarthouse_android.R;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class DoorControlFragment extends BaseControlFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_door, container, false);
        return view;
    }
}
