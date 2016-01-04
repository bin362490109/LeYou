package com.fjby.travel.baidulibrary.utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by abin on 2015/11/6.
 */
public class LocationUtils {
    private static LocationClient mLocClient;
    private   BDLocationListener mBdLocationListener;
    public  LocationUtils (Context c) {
        if (mLocClient == null) {
            mLocClient = new LocationClient(c.getApplicationContext());
            // 定位相关
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);// 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setIsNeedAddress(true);
            option.setScanSpan(10*1000);
            // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mLocClient.setLocOption(option);
            mLocClient.start();
        }
    }

    public  void registerLocation(BDLocationListener bdLocationListener) {
          mBdLocationListener=bdLocationListener;
        mLocClient.registerLocationListener(mBdLocationListener);
    }

    public void unregisterLocation(){
            mLocClient.unRegisterLocationListener(mBdLocationListener);
            mBdLocationListener=null;
    }
    public void mStart(){
        mLocClient.start();
    }
    public void mRequestLocation(){
        mLocClient.requestLocation();
    }
    public void mStop(){
        mLocClient.stop();
    }
}
