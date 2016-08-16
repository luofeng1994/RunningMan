package com.luofeng.runningman.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.luofeng.runningman.Adapter.RunRecordAdapter;
import com.luofeng.runningman.R;
import com.luofeng.runningman.activity.RecordShowActivity;
import com.luofeng.runningman.db.RunningManDB;
import com.luofeng.runningman.model.RunRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗峰 on 2016/8/13.
 */
public class LishiFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private RunRecordAdapter runRecordAdapter;
    private List<RunRecord> runRecordsList = new ArrayList<RunRecord>();
    private RunningManDB runningManDB;
    private RunRecord clickedRunRecord = null;
    private RelativeLayout clickedMessageLayout = null;
    private ImageView clickedDeleteImage = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lishi, container, false);
        runningManDB = RunningManDB.getInstance(getActivity());
        initRunRecordsList();
        listView = (ListView) view.findViewById(R.id.lishi_list_view);
        runRecordAdapter = new RunRecordAdapter(getActivity(), R.layout.run_record_item_layout, runRecordsList);
        listView.setAdapter(runRecordAdapter);
        listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        listView.setOnItemClickListener(this);

        return view;

    }

    private void initRunRecordsList() {
        runRecordsList.clear();
        runRecordsList = runningManDB.loadRunRecord();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refreshRunRecordsList();
        }

    }

    private void refreshRunRecordsList() {
        runRecordsList.clear();
        runRecordsList.addAll(runningManDB.loadRunRecord());//之前用runRecordsList=runningManDB.loadRunRecord()，看似跟新的runRecordsList的数据，实际上改变了runRecordsList的指向，所以adapter的数据源变了，所以listView更新失败。
        runRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        clickedRunRecord = runRecordsList.get(i);
        clickedMessageLayout = (RelativeLayout) view.findViewById(R.id.message_layout);
        clickedDeleteImage = (ImageView) view.findViewById(R.id.delete_image);
        clickedMessageLayout.setOnClickListener(this);
        clickedDeleteImage.setOnClickListener(this);
/*
        RunRecord record = runRecordsList.get(i);
        Intent intent = new Intent(getActivity(), RecordShowActivity.class);
        intent.putExtra("runRecord", record);
        startActivity(intent);
*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_layout :
                Intent intent = new Intent(getActivity(), RecordShowActivity.class);
                intent.putExtra("runRecord", clickedRunRecord);
                startActivity(intent);
                break;
            case R.id.delete_image :
                Log.d("test",clickedDeleteImage.getTag()+"");
                new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("您确定要删除此次纪录吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                break;
        }
    }
}
