package com.fjby.travel.baidulibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.fjby.travel.baidulibrary.listener.MyLocationListener;

public class LocationReceiver extends BroadcastReceiver {
    public final  static String Location_ACTION="location_cation";
    private Context ct = null;
    private LocationReceiver receiver = null;
    private MyLocationListener mLocationListener;
    private  double x=0.0;
    private  double y=0.0;
    private  String addr="";
    // 定位相关
    private LocationClient mLocClient;

    public LocationReceiver(Context c, MyLocationListener.OnLocationListener locationListener) {
        ct = c;
        receiver = this;
        mLocationListener.setOnLocationListener(locationListener);
        // 定位初始化
        mLocClient = new LocationClient(c);
        mLocClient.registerLocationListener(new MyBDLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    // 注册
    public void registerAction() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Location_ACTION);
        ct.registerReceiver(receiver, filter);
    }

    // 注销
    public void unregisterReceiver() {
        ct.unregisterReceiver(receiver);
        ct = null;
        receiver = null;
        mLocClient=null;
    }

    public void onReceive(Context context, Intent intent) {
            mLocationListener.setCurrentLocation(x,y,addr);
    }



    /**
     * 定位SDK监听函数
     */
    public class MyBDLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null )
                return;

            x=location.getLatitude();
            y=location.getLongitude();
            addr=location.getCity();
            Log.v("crb","onReceiveLocation    location = "+ location.getLocationDescribe());
        }
    }

}
