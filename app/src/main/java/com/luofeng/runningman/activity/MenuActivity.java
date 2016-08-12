package com.luofeng.runningman.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luofeng.runningman.R;

/**
 * Created by 罗峰 on 2016/8/4.
 */
public class MenuActivity extends Activity implements View.OnClickListener{
    private RelativeLayout putongModeLayout;
    private RelativeLayout mubiaoModeLayout;
    private RelativeLayout jianzhiModeLayout;
    private TextView lishiText;
    private TextView paobuText;
    private TextView gerenText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_layout2);
        putongModeLayout = (RelativeLayout) findViewById(R.id.putongMode_layout);
        mubiaoModeLayout = (RelativeLayout) findViewById(R.id.mubiaoMode_layout);
        jianzhiModeLayout = (RelativeLayout) findViewById(R.id.jianzhiMode_layout) ;
        lishiText = (TextView) findViewById(R.id.lishi_text);
        paobuText = (TextView) findViewById(R.id.paobu_text);
        gerenText = (TextView) findViewById(R.id.geren_text);

        putongModeLayout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.putongMode_layout:
                Intent intent = new Intent(MenuActivity.this, PutongModeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mubiaoMode_layout:
                break;
            case R.id.jianzhiMode_layout:
                break;
            default:
                break;
        }
    }
}
