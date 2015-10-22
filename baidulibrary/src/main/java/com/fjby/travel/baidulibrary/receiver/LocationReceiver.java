package com.fjby.travel.baidulibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


public class LocationReceiver extends BroadcastReceiver {
    public static String currentLocation = "com.fjby.travel.baidu.currentlocation";
    private OnLocationListener onLocationListener;
    // 定位相关
    private LocationClient mLocClient;
    LocationClientOption option = new LocationClientOption();
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();
        // 定位初始化
        mLocClient = new LocationClient(context);
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        if (s.equals(currentLocation)) {
            if (onLocationListener!=null){
                mLocClient.registerLocationListener(new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        Log.d("crb", "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
                    }
                });
                onLocationListener.onLocation(1.0,1.0);
            }

        } else if (s.equals(currentLocation)) {
            Log.d("crb", "网络出错");
        }
    }


    public interface OnLocationListener {
        void onLocation(double x, double y);
    }

    public void setOnLocationListener(OnLocationListener listener) {
        onLocationListener = listener;
    }
}

