package com.fjby.travel.leyou.activity;

import android.os.Bundle;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyAppealOneFragment;

public class MyAppealActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myappeal);
        initToolbar(true, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.myappeal_framlayout, new MyAppealOneFragment()).commit();
        setToolbarTitle(R.string.myappeal_title);
    }

}
