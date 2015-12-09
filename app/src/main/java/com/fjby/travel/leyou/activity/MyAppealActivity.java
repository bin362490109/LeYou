package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyAppealFragment;
import com.fjby.travel.leyou.fragment.MyAppealOneFragment;

public class MyAppealActivity extends BaseActivity {

    public String titile="";
    public String content="";
    @Override
    protected void setView() {
        setContentView(R.layout.activity_fragment);
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
        repalceFragment( new MyAppealFragment());
    }
}
