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
public class WeightPickDialogUtils implements NumberPicker.OnValueChangeListener{
    private static final int INIT_WEIGHT = 50;
    private static final int MAX_WEIGHT = 200;
    private static final int MIN_WEIGHT = 30;

    private Activity activity;
    private NumberPicker weightPicker;
    private AlertDialog ad;
    private int weight;
    @Override
    public void onValueChange(NumberPicker view, int i, int i1) {
        weight = weightPicker.getValue();
        ad.setTitle(String.valueOf(weight));
    }

    public WeightPickDialogUtils(Activity activity) {
        this.activity = activity;
    }

    private void init(NumberPicker weightPicker) {
        weightPicker.setOnValueChangedListener(this);
        weightPicker.setMaxValue(MAX_WEIGHT);
        weightPicker.setMinValue(MIN_WEIGHT);
        weightPicker.setValue(INIT_WEIGHT);
    }

    public AlertDialog weightPickDialong(final TextView inputWeight) {
        LinearLayout weightChooseLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.weight_choose, null);
        weightPicker = (NumberPicker) weightChooseLayout.findViewById(R.id.weightpicker);
        init(weightPicker);

        ad = new AlertDialog.Builder(activity)
                .setTitle(String.valueOf(INIT_WEIGHT))
                .setView(weightChooseLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputWeight.setText(String.valueOf(weight));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputWeight.setText("");
                    }
                }).show();
        onValueChange(null, 0, 0);
        return  ad;
    }
}
