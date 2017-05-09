package com.example.yjy.smarthouse_android.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjy.smarthouse_android.R;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class ControlContainerFragment extends BaseFragment implements ControlFragment.OnControlFragmentReplace{
    private ControlFragment mControlFragment = new ControlFragment();
    private BaseFragment mLightControlFragment = new LightControlFragment();
    private BaseFragment mWaterElecControlFragment = new WaterElecControlFragment();
    private BaseFragment mDoorControlFragment = new DoorControlFragment();
    private BaseFragment mTempHumControlFragment = new TempHumControlFragment();
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_container, container, false);
        mFragmentManager = getActivity().getSupportFragmentManager();
        mControlFragment.setDlg(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTransaction ft =  mFragmentManager.beginTransaction();
        ft.add(R.id.control_container_fl, mControlFragment);
        ft.commit();
    }

    @Override
    public void onReplace(View v) {
        FragmentTransaction ft =  mFragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.light_control_btn:
                ft.replace(R.id.control_container_fl, mLightControlFragment);
                break;
            case R.id.water_elec_control_btn:
                ft.replace(R.id.control_container_fl, mWaterElecControlFragment);
                break;
            case R.id.door_control_btn:
                ft.replace(R.id.control_container_fl, mDoorControlFragment);
                break;
            case R.id.temp_hum_control_btn:
                ft.replace(R.id.control_container_fl, mTempHumControlFragment);
                break;
            default:
                break;
        }
        ft.addToBackStack(null);
        ft.commit();
        Log.d(TAG, "replace ok");
    }
}
