package com.luofeng.runningman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.model.RunRecord;
import com.luofeng.runningman.util.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 罗峰 on 2016/8/14.
 */
public class RecordShowActivity extends Activity implements View.OnClickListener {

    private RunRecord runRecord;
    private ImageView screenshorImage;
    private TextView modeText;
    private TextView timeText;
    private TextView distanceText;
    private ImageView shareImage;
    private String dateString;
    private File file;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.record_show_layout);
        Intent intent = getIntent();
        runRecord = (RunRecord) intent.getSerializableExtra("runRecord");
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#56627c"));
            tintManager.setStatusBarTintEnabled(true);
        }

        initView();
        setContent();
        initEvent();



    }

    private void initEvent() {
        shareImage.setOnClickListener(this);
    }

    private void initView() {
        screenshorImage = (ImageView) findViewById(R.id.screenshot_image);
        modeText = (TextView) findViewById(R.id.mode_text);
        timeText = (TextView) findViewById(R.id.time_text);
        distanceText = (TextView) findViewById(R.id.distance_text);
        shareImage = (ImageView) findViewById(R.id.share_image);
    }

    private void setContent() {
        modeText.setText(runRecord.getMode());
        timeText.setText(runRecord.getDuration());
        distanceText.setText(runRecord.getDistance());

        String imageName = runRecord.getDateTime().replace("/", "-").replace(":", "-");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile";
        String fileName = path + "/" + imageName + ".png";
        Bitmap imageBitmap = null;
        if (imageBitmap == null) {
            imageBitmap = BitmapFactory.decodeFile(fileName, null);
        }
        screenshorImage.setImageBitmap(imageBitmap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_image:
                saveScreenshot(view);

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra("Kdescription", "hello, from RunningMan");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
        }
    }

    private void saveScreenshot(View v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        dateString = dateFormat.format(new java.util.Date());
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile/shareImage");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folder.getAbsolutePath() +"/" + dateString + ".png";
        file = new File(filePath);
        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        View view = v.getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if(bitmap != null) {
            System.out.println("bitmap got!");
            try{
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("bitmap is NULL!");
        }
    }
}
