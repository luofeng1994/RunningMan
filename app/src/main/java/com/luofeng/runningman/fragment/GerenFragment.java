package com.luofeng.runningman.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luofeng.runningman.R;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class GerenFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }
}
