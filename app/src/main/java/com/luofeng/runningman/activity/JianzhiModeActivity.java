package com.luofeng.runningman.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.luofeng.runningman.R;
import com.luofeng.runningman.db.RunningManDB;
import com.luofeng.runningman.util.SystemBarTintManager;

/**
 * Created by 罗峰 on 2016/8/20.
 */
public class JianzhiModeActivity extends Activity {
    private SystemBarTintManager tintManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#56627c"));
            tintManager.setStatusBarTintEnabled(true);

        }
        setContentView(R.layout.jianzhi_mode_layout);

    }
}
