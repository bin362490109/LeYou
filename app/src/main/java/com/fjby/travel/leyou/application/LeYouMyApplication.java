package com.fjby.travel.leyou.application;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.SDKInitializer;
import com.fjby.travel.leyou.pojo.CityAd;
import com.fjby.travel.leyou.pojo.Tourist;
import com.fjby.travel.leyou.pojo.User;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.NetworkUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by abin on 2015/9/9.
 */
public class LeYouMyApplication extends Application {
    /**
     * The activity list.
     */
    private static Stack<Activity> activitiesStack = new Stack<Activity>();
    public static int versionCode;
    public static String versionName;
    public static User mUser = null;
    public static String mCashHhid;
    public static String imei;
    public static String ip;
    public static String device;
    public static String number;
    public static int screenWidth;
    public static int screenHeight;
    public static  List<CityAd> cityAdList=null;
    //返回推荐景点
    public static List<Tourist> touristList=null;
    @Override
    public void onCreate() {
        mCashHhid = "-1";
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
       MyVolley.init(getApplicationContext());
        // 获取包实例
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = null;
        ApplicationInfo ai = null;
        try {
            // 获取包信息
            pi = pm.getPackageInfo(getPackageName(), 0);
            // 包版本编码
            versionCode = pi.versionCode;
            // 包版本名称
            versionName = pi.versionName;
            // Get TelephonyManager
            TelephonyManager mngr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            imei = mngr.getDeviceId();
            device = android.os.Build.DEVICE;
            number = mngr.getLine1Number() == null ? mngr.getSimSerialNumber() : mngr.getLine1Number();
            ip = NetworkUtils.getIpAddress();
            // 获取应用信息
            ai = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            LogUtil.d("[pi]=" + pi + "   [versioncode]=" + LeYouMyApplication.versionCode + "    [versionName]=" + LeYouMyApplication.versionName + "   [imei]=" + imei + "   [device]=" + device + "   [screenWidth]=" + screenWidth+ "[ip]="+ip);
            LogUtil.e("-------myapplication---------onCreate----------------");
            // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
           SDKInitializer.initialize(this);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getMessage());
            ToastUtils.showLong(this, e.getMessage());
        }
    }

    public static Activity addActivity(Activity activity) {
        activitiesStack.push(activity);
        return activity;
    }

    public static Activity currentActivity() {
        return activitiesStack.lastElement();
    }


    public static void removeActivity(Activity activity) {
        if(activity == null)
            return ;
        try {
            activitiesStack.remove(activity);
            activity.finish();
            activity = null;
            if(Size()<1){
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void removeAllActivities() {
        if (activitiesStack != null && !activitiesStack.isEmpty()) {
            for (Iterator<Activity> i = activitiesStack.iterator(); i.hasNext(); ) {
                Activity a = i.next();
                if (a != null) {
                    a.finish();
                    a = null;
                }
            }
        }
        activitiesStack.clear();
    }
    public static void removeOhterActivities() {
        if (activitiesStack != null && !activitiesStack.isEmpty()) {
            for (Iterator<Activity> i = activitiesStack.iterator(); i.hasNext(); ) {
                Activity a = i.next();
                if (a != null) {
                    a.finish();
                    a = null;
                }
            }
        }
        activitiesStack.clear();
    }

    public static int Size() {
        return activitiesStack.size();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        activitiesStack = null;
        mUser = null;
    }



}
