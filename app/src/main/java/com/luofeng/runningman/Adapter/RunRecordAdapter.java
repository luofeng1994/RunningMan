package com.luofeng.runningman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luofeng.runningman.R;
import com.luofeng.runningman.model.RunRecord;

import java.util.List;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class RunRecordAdapter extends ArrayAdapter<RunRecord>{
    private int resourceId;

    public RunRecordAdapter(Context context, int textViewResourceId, List<RunRecord> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View covertView, ViewGroup parent) {
        RunRecord runRecord = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView dateTime = (TextView) view.findViewById(R.id.item_date_time);
        TextView mode = (TextView) view.findViewById(R.id.item_mode);
        dateTime.setText((runRecord.getDateTime()).replace("/", "\t"));
        mode.setText(runRecord.getMode());
        return view;
    }
}
