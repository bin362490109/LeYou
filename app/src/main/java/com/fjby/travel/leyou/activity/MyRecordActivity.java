package com.fjby.travel.leyou.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyRecordMainFragment;
import com.fjby.travel.leyou.utils.SystemBarTintManager;

public class MyRecordActivity extends BaseActivity {


    @Override
    protected void setView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //  setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.other_bg);
        }
        setContentView(R.layout.activity_record);
        initToolbar(true, true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {
        repalceFragment( new MyRecordMainFragment());
    }

}
