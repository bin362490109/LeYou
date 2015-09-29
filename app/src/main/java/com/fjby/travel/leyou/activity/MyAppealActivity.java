package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyAppealOneFragment;
import com.fjby.travel.leyou.utils.LogUtil;

public class MyAppealActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myappeal);
        initToolbar(true, true);
        repalceFragment( new MyAppealOneFragment());
    }
    protected void repalceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.myappeal_framlayout,fragment);
        ft.commit();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtil.e("onPrepareOptionsMenu   onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }
}
