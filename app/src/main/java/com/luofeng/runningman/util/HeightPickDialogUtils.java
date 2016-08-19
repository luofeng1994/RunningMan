package com.luofeng.runningman.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.luofeng.runningman.R;

/**
 * Created by 罗峰 on 2016/8/19.
 */
public class HeightPickDialogUtils implements NumberPicker.OnValueChangeListener{
    private static final int INIT_HEIGHT = 170;

    private NumberPicker heightPicker;
    private AlertDialog ad;
    private Activity activity;
    private int height;

    public HeightPickDialogUtils(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onValueChange(NumberPicker view, int i, int i1) {
        height = heightPicker.getValue();
        ad.setTitle(String.valueOf(height));
    }
    private void init(NumberPicker numberPicker) {
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setMaxValue(230);
        numberPicker.setMinValue(130);
        numberPicker.setValue(INIT_HEIGHT);
    }
    public AlertDialog heightPickDialog(final TextView inputHeight) {
        LinearLayout heightChooseLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.height_choose, null);
        heightPicker = (NumberPicker) heightChooseLayout.findViewById(R.id.heightpicker);
        init(heightPicker);
        ad = new AlertDialog.Builder(activity)
                .setTitle(String.valueOf(INIT_HEIGHT))
                .setView(heightChooseLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputHeight.setText(String.valueOf(height));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputHeight.setText("");
                    }
                }).show();
        onValueChange(null, 0, 0);
        return ad;
    }


}
