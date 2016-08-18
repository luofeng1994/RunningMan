package com.luofeng.runningman.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.luofeng.runningman.R;
import com.luofeng.runningman.util.BirthdayPickDialogUtil;

/**
 * Created by 罗峰 on 2016/8/18.
 */
public class InfoSetActivity extends Activity implements View.OnClickListener {
    private static final String INIT_DATE = "1994年1月1日";

    private static final int SET_NICKNAME_TAG = 0;
    private ImageView nicknameEditImage;
    private TextView nicknameText;
    private ImageView birthdayEditImage;
    private TextView birthdayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_set_layout);
        nicknameEditImage = (ImageView) findViewById(R.id.nickname_edit_image);
        nicknameText = (TextView) findViewById(R.id.nickname_text);
        birthdayEditImage = (ImageView) findViewById(R.id.birthday_edit_image);
        birthdayText = (TextView) findViewById(R.id.birthday_text);

        nicknameEditImage.setOnClickListener(this);
        birthdayEditImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nickname_edit_image:
                showMyDialog(SET_NICKNAME_TAG);
                break;
            case R.id.birthday_edit_image:
                BirthdayPickDialogUtil birthdayPickDialog = new BirthdayPickDialogUtil(this, INIT_DATE);
                birthdayPickDialog.dateTimePicKDialog(birthdayText);
                break;
        }
    }

    private void showMyDialog(int tag) {
        switch (tag) {
            case SET_NICKNAME_TAG:
                final EditText nicknameInput = new EditText(this);
                nicknameInput.setFocusable(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("亲，输入你想要的昵称吧~");
                builder.setView(nicknameInput);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newNickname = nicknameInput.getText().toString();
                        if (!TextUtils.isEmpty(newNickname)) {
                            nicknameText.setText(newNickname);
                        }
                    }
                }).show();
                break;
        }
    }
}
