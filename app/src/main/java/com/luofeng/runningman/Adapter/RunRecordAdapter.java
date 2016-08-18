package com.luofeng.runningman.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.model.RunRecord;

import java.util.List;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class RunRecordAdapter extends ArrayAdapter<RunRecord>{
    private int resourceId;
    private static final String PUTONG_MODE = "普通模式";
    private static final String MUBIAO_MODE = "目标模式";
    private static final String JIANZHI_MODE = "减脂模式";

    public RunRecordAdapter(Context context, int textViewResourceId, List<RunRecord> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View covertView, ViewGroup parent) {
        RunRecord runRecord = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView dateTime = (TextView) view.findViewById(R.id.item_date_time);
        ImageView modeImage = (ImageView) view.findViewById(R.id.item_mode_image);
/*        ImageView deleteImage = (ImageView) view.findViewById(R.id.delete_image);
        deleteImage.setTag(position);*/
        dateTime.setText((runRecord.getDateTime()).replace("/", "\t"));
        String mode = runRecord.getMode();
        if (mode.equals(PUTONG_MODE))
            modeImage.setImageResource(R.drawable.putong_tag);
        else if (mode.equals(MUBIAO_MODE))
            modeImage.setImageResource(R.drawable.mubiao_tag);
        else if (mode.equals(JIANZHI_MODE))
            modeImage.setImageResource(R.drawable.jianzhi_tag);
        return view;
    }
}
