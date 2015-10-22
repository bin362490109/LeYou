package com.fjby.travel.baidulibrary.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.fjby.travel.baidulibrary.receiver.SDKReceiver;

/**
 * Created by abin on 2015/10/21.
 */
public class BaseActivity extends Activity {

    private SDKReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
    }
}
