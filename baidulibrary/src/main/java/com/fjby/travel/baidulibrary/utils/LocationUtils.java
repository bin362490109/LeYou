package com.fjby.travel.baidulibrary.utils;

import android.app.Activity;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fjby.travel.baidulibrary.listener.MyLocationListener;

/**
 * Created by abin on 2015/11/6.
 */
public class LocationUtils {


    public  static void getLocation(Activity c,final MyLocationListener.OnLocationListener onLocationListener){
        // 定位相关
      final   LocationClient mLocClient= new LocationClient(c);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
      // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                // map view 销毁后不在处理新接收的位置
                if (bdLocation == null) {
                    mLocClient.stop();
                    return;
                }
                onLocationListener.onLocation(bdLocation.getLatitude(), bdLocation.getLongitude(), bdLocation.getCity());
                mLocClient.stop();
            }

        });
    }
}
