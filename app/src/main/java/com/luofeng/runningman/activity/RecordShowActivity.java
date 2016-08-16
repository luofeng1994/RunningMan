package com.luofeng.runningman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.model.RunRecord;

/**
 * Created by 罗峰 on 2016/8/14.
 */
public class RecordShowActivity extends Activity {

    private ImageView screenshorImage;
    private TextView modeText;
    private TextView timeText;
    private TextView distanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.record_show_layout);
        Intent intent = getIntent();
        RunRecord runRecord = (RunRecord) intent.getSerializableExtra("runRecord");
        screenshorImage = (ImageView) findViewById(R.id.screenshot_image);
        modeText = (TextView) findViewById(R.id.mode_text);
        timeText = (TextView) findViewById(R.id.time_text);
        distanceText = (TextView) findViewById(R.id.distance_text);
        modeText.setText(runRecord.getMode());
        timeText.setText(runRecord.getDuration());
        distanceText.setText(runRecord.getDistance());

        String imageName = runRecord.getDateTime().replace("/", "-").replace(":", "-");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RunningManFile";
        String fileName = path + "/" + imageName + ".png";

        Log.w("test", fileName);
        Bitmap imageBitmap = null;
        if (imageBitmap == null) {
            imageBitmap = BitmapFactory.decodeFile(fileName, null);
        }
        screenshorImage.setImageBitmap(imageBitmap);
    }
}
