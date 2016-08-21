package com.luofeng.runningman.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.activity.InfoSetActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class GerenFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView setImage;
    private ImageView avatarImage;
    private TextView nicknameText;
    private TextView birthdayText;
    private TextView genderText;
    private TextView heightText;
    private TextView weightText;
    private boolean isUserInfoSetted = false;

    private static final String INIT_NICKNAME = "昵称";
    private static final String INIT_BIRTHDAY = "1994.01.01";
    private static final String INIT_GENDER = "女";
    private static final String INIT_HEIGHT = "170";
    private static final String INIT_WEIGHT = "60";
    private static final String FILE_NAME = "avatar";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initEvent();
        setText();
        return view;
    }

    private void initView() {
        setImage = (ImageView) view.findViewById(R.id.set_image);
        avatarImage = (ImageView) view.findViewById(R.id.avatar_image);
        nicknameText = (TextView) view.findViewById(R.id.nickname_text);
        birthdayText = (TextView) view.findViewById(R.id.birthday_text);
        genderText = (TextView) view.findViewById(R.id.gender_text);
        heightText = (TextView) view.findViewById(R.id.height_text);
        weightText = (TextView) view.findViewById(R.id.weight_text);

    }

    private void initEvent() {
        setImage.setOnClickListener(this);

    }

    private void setText() {

        SharedPreferences pref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        isUserInfoSetted = pref.getBoolean("user_info_setted", false);
        if (isUserInfoSetted) {
            nicknameText.setText(pref.getString("nickname", INIT_NICKNAME));
            birthdayText.setText(pref.getString("birthday", INIT_BIRTHDAY));
            genderText.setText(pref.getString("gender", INIT_GENDER));
            heightText.setText(pref.getString("height", INIT_HEIGHT));
            weightText.setText(pref.getString("weight", INIT_WEIGHT));

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/avatarfile";
            String fileName = path + "/" + FILE_NAME + ".jpg";
            File  file = new File(fileName);
            if (file.exists()) {
/*                BitmapFactory.Options ops = new BitmapFactory.Options();
                ops.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(fileName, ops);*/
                Bitmap bitmap = BitmapFactory.decodeFile(fileName, null);
                Log.d("test", "gerenFragment,width:" + bitmap.getWidth()+","+bitmap.getHeight());

                avatarImage.setImageBitmap(bitmap);

            }

        } else {
            nicknameText.setText(INIT_NICKNAME);
            birthdayText.setText(INIT_BIRTHDAY);
            genderText.setText(INIT_GENDER);
            heightText.setText(INIT_HEIGHT);
            weightText.setText(INIT_WEIGHT);
        }
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

    public void onResume() {
        super.onResume();
        setText();
    }

}
