package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyRecordMainFragment;

public class MyRecordActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initToolbar(true, true);
        repalceFragment( new MyRecordMainFragment());
    }

}
