package com.luofeng.runningman.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofeng.runningman.R;
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

    private boolean isFirstTimeIn = true;
    private ImageView avaterImage;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_set_layout);

        initView();
        initEvent();
        setOldAvatar();
    }

    private void initView() {
        avaterImage = (ImageView) findViewById(R.id.avatar_image);
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
        avaterImage.setOnClickListener(this);
        nicknameEditImage.setOnClickListener(this);
        birthdayEditImage.setOnClickListener(this);
        heightEditImage.setOnClickListener(this);
        weightEditImage.setOnClickListener(this);
        genderEditImage.setOnClickListener(this);
    }

    private void setOldAvatar() {
        Log.d("test", "setOldAvater");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/avatarfile";
        String fileName = path + "/" + FILE_NAME + ".png";
        File file = new File(fileName);
        if (!file.exists()) {
            Log.d("test", "不存在");
        } else {
            Log.d("test", "存在");
            Bitmap imageBitmap = null;
            if (imageBitmap == null) {
                imageBitmap = BitmapFactory.decodeFile(fileName, null);
            }
            avaterImage.setImageBitmap(imageBitmap);
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
        }
    }

    private void setAvater() {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/avaterfile");
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
                        avaterImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
