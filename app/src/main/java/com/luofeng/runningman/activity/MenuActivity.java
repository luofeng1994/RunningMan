package com.luofeng.runningman.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.fragment.GerenFragment;
import com.luofeng.runningman.fragment.LishiFragment;
import com.luofeng.runningman.fragment.ModeFragment;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout lishiImageLayout;
    private LinearLayout paobuImageLayout;
    private LinearLayout gerenImageLayout;
    private ImageView tagLishi;
    private ImageView tagPaobu;
    private ImageView tagGeren;
    private TextView topText;

    private Fragment lishiFragment;
    private Fragment modeFragment;
    private Fragment gerenFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        setSelect(1);


    }

    private void initView() {
        lishiImageLayout = (LinearLayout) findViewById(R.id.lishi_image_layout);
        paobuImageLayout = (LinearLayout) findViewById(R.id.paobu_image_layout);
        gerenImageLayout = (LinearLayout) findViewById(R.id.geren_image_layout);
        tagLishi = (ImageView) findViewById(R.id.tag_lishi);
        tagPaobu = (ImageView) findViewById(R.id.tag_paobu);
        tagGeren = (ImageView) findViewById(R.id.tag_geren);
        tagLishi.setVisibility(View.INVISIBLE);
        tagPaobu.setVisibility(View.VISIBLE);
        tagGeren.setVisibility(View.INVISIBLE);
        topText = (TextView) findViewById(R.id.top_text);
    }

    private void initEvent() {

        lishiImageLayout.setOnClickListener(this);
        paobuImageLayout.setOnClickListener(this);
        gerenImageLayout.setOnClickListener(this);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                if (lishiFragment == null) {
                    lishiFragment = new LishiFragment();
                    transaction.add(R.id.content_layout, lishiFragment);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(lishiFragment);
                }
                topText.setText("历史纪录");

                break;
            case 1:
                if (modeFragment == null) {
                    modeFragment = new ModeFragment();
                    transaction.add(R.id.content_layout, modeFragment);
                }else {
                    transaction.show(modeFragment);
                }
                topText.setText("跑步");

                break;
            case 2:
                if (gerenFragment == null) {
                    gerenFragment = new GerenFragment();
                    transaction.add(R.id.content_layout, gerenFragment);
                }else {
                    transaction.show(gerenFragment);
                }
                topText.setText("个人中心");

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
            case R.id.lishi_image_layout://当点击微信按钮时，切换图片为亮色，切换fragment为微信聊天界面
                tagLishi.setVisibility(View.VISIBLE);
                tagPaobu.setVisibility(View.INVISIBLE);
                tagGeren.setVisibility(View.INVISIBLE);
                setSelect(0);
                break;
            case R.id.paobu_image_layout:
                tagLishi.setVisibility(View.INVISIBLE);
                tagPaobu.setVisibility(View.VISIBLE);
                tagGeren.setVisibility(View.INVISIBLE);
                setSelect(1);
                break;
            case R.id.geren_image_layout:
                tagLishi.setVisibility(View.INVISIBLE);
                tagPaobu.setVisibility(View.INVISIBLE);
                tagGeren.setVisibility(View.VISIBLE);
                setSelect(2);
                break;
            default:
                break;
        }
    }
}
