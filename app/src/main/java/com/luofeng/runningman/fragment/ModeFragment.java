package com.luofeng.runningman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.luofeng.runningman.R;
import com.luofeng.runningman.activity.JianzhiModeActivity;
import com.luofeng.runningman.activity.MubiaoModeActivity;
import com.luofeng.runningman.activity.PutongModeActivity;


public class ModeFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout putongModeLayout;
    private RelativeLayout mubiaoModeLayout;
    private RelativeLayout jianzhiModeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mode, container, false);

        putongModeLayout = (RelativeLayout) view.findViewById(R.id.putongMode_layout);
        mubiaoModeLayout = (RelativeLayout) view.findViewById(R.id.mubiaoMode_layout);
        jianzhiModeLayout = (RelativeLayout) view.findViewById(R.id.jianzhiMode_layout);
        putongModeLayout.setOnClickListener(this);
        mubiaoModeLayout.setOnClickListener(this);
        jianzhiModeLayout.setOnClickListener(this);
        return view;

    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.putongMode_layout:
                intent = new Intent(getActivity(), PutongModeActivity.class);
                startActivity(intent);
                break;
            case R.id.mubiaoMode_layout:
                intent = new Intent(getActivity(), MubiaoModeActivity.class);
                startActivity(intent);
                break;
            case R.id.jianzhiMode_layout:
                intent = new Intent(getActivity(), JianzhiModeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
