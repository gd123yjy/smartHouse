package com.example.yjy.smarthouse_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjy.smarthouse_android.R;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class ControlFragment extends BaseControlFragment implements View.OnClickListener{
    public interface OnControlFragmentReplace{
        void onReplace(View view);
    }

    private OnControlFragmentReplace mDlg = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initView();
    }

    public void setDlg(OnControlFragmentReplace dlg){
        mDlg = dlg;
    }

    private void initView() {
        View view = getView();
       view.findViewById(R.id.light_control_btn).setOnClickListener(this);
       view.findViewById(R.id.water_elec_control_btn).setOnClickListener(this);
       view.findViewById(R.id.door_control_btn).setOnClickListener(this);
       view.findViewById(R.id.temp_hum_control_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       if(mDlg!=null){
           mDlg.onReplace(v);
       }
    }
}
