package com.luofeng.runningman.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luofeng.runningman.R;
import com.luofeng.runningman.model.User;
import com.luofeng.runningman.util.BirthdayPickDialogUtils;
import com.luofeng.runningman.util.GenderPickDialogUtils;
import com.luofeng.runningman.util.HeightPickDialogUtils;
import com.luofeng.runningman.util.WeightPickDialogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 罗峰 on 2016/8/18.
 */
public class InfoSetActivity extends Activity implements View.OnClickListener {
    private static final String INIT_DATE = "1994年1月1日";
    private static final String FILE_NAME = "avatar";
    private static final int SET_NICKNAME_TAG = 0;
    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;

    private boolean isUserInfoSetted = false;
    private static final String INIT_NICKNAME = "昵称";
    private static final String INIT_BIRTHDAY = "1994.01.01";
    private static final String INIT_GENDER = "女";
    private static final String INIT_HEIGHT = "170";
    private static final String INIT_WEIGHT = "60";

    private boolean isFirstTimeIn = true;
    private ImageView avatarImage;
    private ImageView nicknameEditImage;
    private TextView nicknameText;
    private ImageView birthdayEditImage;
    private TextView birthdayText;
    private ImageView heightEditImage;
    private TextView heightText;
    private ImageView weightEditImage;
    private TextView weightText;
    private ImageView genderEditImage;
    private TextView genderText;
    private Uri imageUri;
    private Button saveButton;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_set_layout);

        initView();
        initEvent();
        setOldInfo();
    }

    private void initView() {
        avatarImage = (ImageView) findViewById(R.id.avatar_image);
        saveButton = (Button) findViewById(R.id.saveUserInfo);
        nicknameText = (TextView) findViewById(R.id.nickname_text);
        birthdayText = (TextView) findViewById(R.id.birthday_text);
        heightText = (TextView) findViewById(R.id.height_text);
        weightText = (TextView) findViewById(R.id.weight_text);
        genderText = (TextView) findViewById(R.id.gender_text);
        nicknameEditImage = (ImageView) findViewById(R.id.nickname_edit_image);
        birthdayEditImage = (ImageView) findViewById(R.id.birthday_edit_image);
        heightEditImage = (ImageView) findViewById(R.id.height_edit_image);
        weightEditImage = (ImageView) findViewById(R.id.weight_edit_image);
        genderEditImage = (ImageView) findViewById(R.id.gender_edit_image);
    }

    private void initEvent() {
        avatarImage.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        nicknameEditImage.setOnClickListener(this);
        birthdayEditImage.setOnClickListener(this);
        heightEditImage.setOnClickListener(this);
        weightEditImage.setOnClickListener(this);
        genderEditImage.setOnClickListener(this);
    }

    private void setOldInfo() {


        SharedPreferences pref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        isUserInfoSetted = pref.getBoolean("user_info_setted", false);
        if (isUserInfoSetted) {
            nicknameText.setText(pref.getString("nickname", INIT_NICKNAME));
            birthdayText.setText(pref.getString("birthday", INIT_BIRTHDAY));
            genderText.setText(pref.getString("gender", INIT_GENDER));
            heightText.setText(pref.getString("height", INIT_HEIGHT));
            weightText.setText(pref.getString("weight", INIT_WEIGHT));

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/avatarfile";
            String fileName = path + "/" + FILE_NAME + ".png";
            File  file = new File(fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(fileName, null);
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
            case R.id.nickname_edit_image:
                showMyDialog(SET_NICKNAME_TAG);
                break;
            case R.id.birthday_edit_image:
                BirthdayPickDialogUtils birthdayPickDialog = new BirthdayPickDialogUtils(this, INIT_DATE);
                birthdayPickDialog.dateTimePicKDialog(birthdayText);
                break;
            case R.id.height_edit_image:
                HeightPickDialogUtils heightPickDialogUtils = new HeightPickDialogUtils(this);
                heightPickDialogUtils.heightPickDialog(heightText);
                break;
            case R.id.weight_edit_image:
                WeightPickDialogUtils weightPicDialogUtils = new WeightPickDialogUtils(this);
                weightPicDialogUtils.weightPickDialong(weightText);
                break;
            case R.id.gender_edit_image:
                GenderPickDialogUtils genderPickDialogUtils = new GenderPickDialogUtils(this);
                genderPickDialogUtils.genderPickDialog(genderText);
                break;
            case R.id.avatar_image:
                setAvater();
                break;
            case R.id.saveUserInfo:
                saveUserInfo();
                break;
        }
    }

    private void saveUserInfo() {
        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();

        user = new User(nicknameText.getText().toString(), birthdayText.getText().toString(), genderText.getText().toString(), heightText.getText().toString(), weightText.getText().toString());
        editor.putBoolean("user_info_setted", true);
        editor.putString("nickname", user.getNickname());
        editor.putString("birthday", user.getBirthday());
        editor.putString("gender", user.getGender());
        editor.putString("height", user.getHeight());
        editor.putString("weight", user.getWeight());
        editor.commit();
        Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
    }

    private void setAvater() {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/avatarfile");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filepath = folder.getAbsolutePath() + "/" + FILE_NAME + ".png";
        File avatarPhoto = new File(filepath);
        if (avatarPhoto.exists()) {
            avatarPhoto.delete();
        }
        try {
            avatarPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(avatarPhoto);//将File对象转换成Uri对象,这个Uri 对象标识着这张图片的唯一地址
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

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

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        avatarImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}
