package com.luofeng.runningman.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luofeng.runningman.R;

/**
 * Created by 罗峰 on 2016/8/19.
 */
public class GenderPickDialogUtils implements RadioGroup.OnCheckedChangeListener {
    private static final String MALE = "男";
    private static final String FEMALE = "女";

    private Activity activity;
    private AlertDialog ad;
    private RadioGroup genderRadio;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private String gender = FEMALE;


    public GenderPickDialogUtils(Activity activity) {
        this.activity = activity;
    }

    public AlertDialog genderPickDialog(final TextView inputGender) {
        LinearLayout genderChooseLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.gender_choose, null);
        genderRadio = (RadioGroup) genderChooseLayout.findViewById(R.id.gender_radio);
        maleButton = (RadioButton) genderChooseLayout.findViewById(R.id.male_button);
        femaleButton = (RadioButton) genderChooseLayout.findViewById(R.id.female_button);
        genderRadio.setOnCheckedChangeListener(this);

        ad = new AlertDialog.Builder(activity)
                .setTitle("没有第三种哦~")
                .setView(genderChooseLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputGender.setText(gender);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
        return ad;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (maleButton.getId() == checkedId) {
            gender = MALE;
        }
        else if (femaleButton.getId() == checkedId) {
            gender = FEMALE;
        }
    }
}
