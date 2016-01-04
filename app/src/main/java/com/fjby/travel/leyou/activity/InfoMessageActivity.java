package com.fjby.travel.leyou.activity;

import android.os.Build;
import android.os.Bundle;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.SystemBarTintManager;

/**
 * Created by abin on 2015/9/17.
 */
public class InfoMessageActivity extends BaseActivity {

    @Override
    protected void setView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //  setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.other_bg);
        }
        setContentView(R.layout.activity_info_message);
        initToolbar(true, true);
        setToolbarBackground(R.color.other_bg);
        setToolbarTitle(R.string.info_message_title);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {

    }
}
