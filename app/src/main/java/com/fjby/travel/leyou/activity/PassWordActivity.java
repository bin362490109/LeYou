package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.PassChangeFragment;
import com.fjby.travel.leyou.fragment.PassRegisterFragment;
import com.fjby.travel.leyou.fragment.PassResetFragment;
import com.fjby.travel.leyou.fragment.UpdateInfoFragment;

public class PassWordActivity extends BaseActivity {

    public  static final int ResetPass=0;
    public  static final int ChangePass=1;
    public  static final int RegiserPass=2;
    public  static final int updateInfo=3;
    public  static final String PassType="pass_type";
    private int type;
    @Override
    protected void setView() {
        type=   getIntent().getExtras().getInt(PassType,2);
        setContentView(R.layout.activity_fragment);
        initToolbar(true, true);
        if (type%2==0) {
            CoordinatorLayout coordinatorLayout=(CoordinatorLayout)findViewById(R.id.fragment_coord);
            coordinatorLayout.setBackgroundResource(R.drawable.login_bg);
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {
        switch (type){
            case ResetPass:
                repalceFragment(new PassResetFragment());
                break;
            case ChangePass:
                repalceFragment(new PassChangeFragment());
                break;
            case RegiserPass:
                repalceFragment(new PassRegisterFragment());
                break;
            case updateInfo:
                repalceFragment(new UpdateInfoFragment());
                break;
        }
    }
}
