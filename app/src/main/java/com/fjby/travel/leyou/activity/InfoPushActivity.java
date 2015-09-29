package com.fjby.travel.leyou.activity;

import android.os.Bundle;

import com.fjby.travel.leyou.R;

/**
 * Created by abin on 2015/9/17.
 */
public class InfoPushActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_push);
        initToolbar(true, true);
        setToolbarTitle(R.string.info_push_title);
    }

}
