package com.luofeng.runningman.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.luofeng.runningman.R;
import com.luofeng.runningman.fragment.GerenFragment;
import com.luofeng.runningman.fragment.LishiFragment;
import com.luofeng.runningman.fragment.ModeFragment;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView lishiImage;
    private ImageView paobuImage;
    private ImageView gerenImage;
    private TextView topText;

    private Fragment lishiFragment;
    private Fragment modeFragment;
    private Fragment gerenFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Log.d("test", width + "," + height + "," + density + "," + densityDpi);

        initView();
        initEvent();
        setSelect(1);


    }

    private void initView() {
        lishiImage = (ImageView) findViewById(R.id.lishi_image);
        paobuImage = (ImageView) findViewById(R.id.paobu_image);
        gerenImage = (ImageView) findViewById(R.id.geren_image);
        topText = (TextView) findViewById(R.id.top_text);
    }

    private void initEvent() {

        lishiImage.setOnClickListener(this);
        paobuImage.setOnClickListener(this);
        gerenImage.setOnClickListener(this);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                topText.setText("历史纪录");
                if (lishiFragment == null) {
                    lishiFragment = new LishiFragment();
                    transaction.add(R.id.content_layout, lishiFragment);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(lishiFragment);
                }
                break;
            case 1:
                topText.setText("跑步");
                if (modeFragment == null) {
                    modeFragment = new ModeFragment();
                    transaction.add(R.id.content_layout, modeFragment);
                }else {
                    transaction.show(modeFragment);
                }
                break;
            case 2:
                topText.setText("个人中心");
                if (gerenFragment == null) {
                    gerenFragment = new GerenFragment();
                    transaction.add(R.id.content_layout, gerenFragment);
                }else {
                    transaction.show(gerenFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();//提交事务
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (lishiFragment != null) {
            transaction.hide(lishiFragment);
        }
        if (modeFragment != null) {
            transaction.hide(modeFragment);
        }
        if (gerenFragment != null) {
            transaction.hide(gerenFragment);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lishi_image://当点击微信按钮时，切换图片为亮色，切换fragment为微信聊天界面
                setSelect(0);
                break;
            case R.id.paobu_image:
                setSelect(1);
                break;
            case R.id.geren_image:
                setSelect(2);
                break;
            default:
                break;
        }
    }
}
