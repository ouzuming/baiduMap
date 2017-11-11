package com.example.lbsMpa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class map1Activity extends AppCompatActivity {
    private BitmapDescriptor mMarker;
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private UiSettings mUiSettings;
    private boolean isFirstLocate = true;
    private int CB_COUNTER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new map1Activity.MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map1);
        Toast.makeText(map1Activity.this, "map1Activity", Toast.LENGTH_SHORT).show();
        mapView = (MapView) findViewById(R.id.bmapView);
        mapView.showScaleControl(true);  //比例尺
        mapView.showZoomControls(true);  //缩放按钮
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        UiSettings mUiSettings = baiduMap.getUiSettings();  //指南针
        mUiSettings.setCompassEnabled(true);
        positionText = (TextView) findViewById(R.id.position_text_View);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(map1Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(map1Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(map1Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            Toast.makeText(map1Activity.this, "perssion", Toast.LENGTH_SHORT).show();
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(map1Activity.this, permissions, 1);
        } else {
            Toast.makeText(map1Activity.this, "start", Toast.LENGTH_SHORT).show();
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        //共有三种坐标可选
        //1. gcj02：国测局坐标；
        //2. bd09：百度墨卡托坐标；
        //3. bd09ll：百度经纬度坐标；

        int span = 5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        //加载设置
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void navigateTo(BDLocation location) {
        //构造定位数据
        MyLocationData data = new MyLocationData.Builder()
                .accuracy(location.getRadius())//定位精度
                .latitude(location.getLatitude())//纬度
                .longitude(location.getLongitude())//经度
                .direction(100)//方向 可利用手机方向传感器获取 此处为方便写死
                .build();
        //设置定位数据
        baiduMap.setMyLocationData(data);

        //配置定位图层显示方式
        //有两个不同的构造方法重载 分别为三个参数和五个参数的
        //这里主要讲一下常用的三个参数的构造方法
        //三个参数：LocationMode(定位模式：罗盘，跟随),enableDirection（是否允许显示方向信息）
        // ,customMarker（自定义图标）
        MyLocationConfiguration configuration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mMarker);

        baiduMap.setMyLocationConfiguration(configuration);

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        //第一次定位需要更新下地图显示状态
        if (isFirstLocate) {
            isFirstLocate = false;
            MapStatus.Builder builder = new MapStatus.Builder()
                    .target(ll)//地图缩放中心点
                    .zoom(18f);//缩放倍数 百度地图支持缩放21级 部分特殊图层为20级
            //改变地图状态
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            CB_COUNTER++;
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(location.getLatitude());
            currentPosition.append("经度：").append(location.getLongitude()).append("\n");
            currentPosition.append("位置：").append(location.getCountry())
                    .append(location.getProvince())
                    .append(location.getCity())
                    .append(location.getDistrict())
                    .append(location.getStreet()).append("\n");
            currentPosition.append("定位方式：");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
                navigateTo(location);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
                navigateTo(location);
            }
            currentPosition.append("\n").append("cb_couner: ").append(CB_COUNTER);
            positionText.setText(currentPosition);
        }
    }
}
