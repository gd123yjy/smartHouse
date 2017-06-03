package com.example.yjy.smarthouse_android.controller.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by Tradoff on 2017/5/7.
 */
public class BaseFragment extends Fragment {
    public String TAG = BaseFragment.class.getSimpleName();

    protected ControlFragment.OnControlFragmentReplace mDlg = null;

    public void setDlg(ControlFragment.OnControlFragmentReplace mDlg) {
        this.mDlg = mDlg;
    }
}
