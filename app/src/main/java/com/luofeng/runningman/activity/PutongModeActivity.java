package com.luofeng.runningman.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.AMap;


import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.luofeng.runningman.R;
import com.luofeng.runningman.db.RunningManDB;
import com.luofeng.runningman.model.RunRecord;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 罗峰 on 2016/8/4.
 */
public class PutongModeActivity extends Activity implements LocationSource, AMapLocationListener, View.OnClickListener, View.OnLongClickListener {
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器
    private MarkerOptions markerOptions = null;

    private ImageView startImage = null;
    private Chronometer timeChronometer = null;
    private TextView distanceText = null;
    private TextView speedText = null;
    private RunningManDB runningManDB;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private boolean isFirstRunLoc = true;
    private boolean startedRun = false;
    private boolean paused = true;
    private boolean isFirstClicked = true;

    private int locNum = 0;
    private double speed = 1;
    private double distance = 0;
    private long recordTime = 0;//计时使用，使得暂停开始之后计时器继续不间断计时
    private LatLng latLngLast = null, latLngNow = null;
    private String startDateTime;

    private long loc_interval = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putong_mode_layout);
        runningManDB = RunningManDB.getInstance(this);
        initView(savedInstanceState);
        //开始第一次定位
        initLoc();
    }

    private void initView(Bundle savedInstanceState) {
        startImage = (ImageView) findViewById(R.id.start_image);
        startImage.setOnClickListener(this);
        startImage.setOnLongClickListener(this);
        timeChronometer = (Chronometer) findViewById(R.id.chronometer);
        timeChronometer.setFormat("00:%s");
        distanceText = (TextView) findViewById(R.id.distance_text);
        speedText = (TextView) findViewById(R.id.speed_text);

        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();


        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);

        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon1));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        /*markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1));
        aMap.addMarker(markerOptions);*/

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_image:
                //第一次点击开始按钮
                if (isFirstClicked) {
                    startedRun = true;
                    isFirstClicked = false;
                    paused = false;
                    startRun();
                    startImage.setImageResource(R.drawable.pause);
                    timeChronometer.setBase(SystemClock.elapsedRealtime());
                    timeChronometer.start();
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");//!!hh:mm:ss则获取的是12小时制，HH:mm:ss则获取的是24小时制
                    startDateTime = sDateFormat.format(new java.util.Date());
                }
                //暂停状态下点击图片，开始跑步,显示暂停按钮
                else if (!isFirstClicked && startedRun && paused) {
                    paused = false;
                    startImage.setImageResource(R.drawable.pause);
                    goOnRun();
                    timeChronometer.setBase(SystemClock.elapsedRealtime() - recordTime);
                    timeChronometer.start();
                }
                //跑步状态下点击图片，暂停跑步,显示开始按钮
                else if (!isFirstClicked && startedRun && !paused) {
                    paused = true;
                    startImage.setImageResource(R.drawable.start);
                    pauseRun();
                    timeChronometer.stop();
                    recordTime = SystemClock.elapsedRealtime() - timeChronometer.getBase();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.start_image:
                if (isFirstClicked) {
                    new AlertDialog.Builder(PutongModeActivity.this).setTitle("提示").setMessage("您还未开始跑步").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                } else {
                    new AlertDialog.Builder(PutongModeActivity.this).setTitle("提示").setMessage("您确定要结束此次跑步吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mLocationClient.stopLocation();
                            timeChronometer.stop();
                            startImage.setImageResource(R.drawable.stop);
                            startImage.setEnabled(false);
                            saveRecord();
                        }
                    }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
                break;
        }
        return true;
    }

    //定位
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        //mLocationOption.setOnceLocation(false);
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        //mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void startRun() {

        /*markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1));
        aMap.addMarker(markerOptions);*/
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(loc_interval);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    //定位回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        //PolylineOptions mPlolylineOption = new PolylineOptions();
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                if (isFirstLoc && !startedRun) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                    markerOptions = new MarkerOptions();
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1));
                    aMap.addMarker(markerOptions);
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_SHORT).show();
                    mLocationClient.stopLocation();
                    isFirstLoc = false;
                } else if (!isFirstLoc && startedRun) {
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    if (isFirstRunLoc) {
                        markerOptions = new MarkerOptions();
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1));
                        aMap.addMarker(markerOptions);
                        latLngLast = new LatLng(0, 0);
                        latLngNow = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                        isFirstRunLoc = false;
                    } else {
                        LatLng tempLatLng = latLngNow.clone();
                        latLngNow = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                        latLngLast = tempLatLng.clone();
                        double distanceInterval = getDistance(latLngLast, latLngNow);
                        if ("gps".equals(amapLocation.getProvider())) {
                            speed = amapLocation.getSpeed();
                        } else {
                            speed = distanceInterval / loc_interval;
                        }

                        if (distanceInterval < 30) {
                            PolylineOptions mPolylineOption = new PolylineOptions();
                            mPolylineOption.add(latLngLast, latLngNow);
                            mPolylineOption.width(15);
                            mPolylineOption.color(Color.GREEN);
                            aMap.addPolyline(mPolylineOption);

                            DecimalFormat df = new DecimalFormat("#####0.00");
                            distance = distance + distanceInterval;
                            distanceText.setText(df.format(distance));
                            speedText.setText(df.format(speed));
                        }


                    }
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private double getDistance(LatLng pointLast,LatLng pointNow) {
        float distance = AMapUtils.calculateLineDistance(pointLast, pointNow);

        return (double)distance;

   }

    private void saveRecord() {
        RunRecord runRecord = new RunRecord();
        runRecord.setMode("普通模式");
        runRecord.setDateTime(startDateTime);
        DecimalFormat df = new DecimalFormat("#####0.00");
        runRecord.setDistance((df.format(distance)).toString());
        runRecord.setDuration(timeChronometer.getText().toString());
        runningManDB.saveRunRecord(runRecord);
    }
    private void pauseRun() {
        mLocationClient.stopLocation();
    }
    private void goOnRun() {
        mLocationClient.startLocation();
    }


    //激活定位
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;

    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
