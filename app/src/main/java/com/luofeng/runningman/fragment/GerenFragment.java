package com.luofeng.runningman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.activity.InfoSetActivity;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class GerenFragment extends Fragment implements View.OnClickListener {

    private ImageView setImage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setImage = (ImageView) view.findViewById(R.id.set_image);
        setImage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_image:
                Intent intent = new Intent(getActivity(), InfoSetActivity.class);
                startActivity(intent);
                break;
        }
    }
}
