package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.fragment.MyAppealOneFragment;
import com.fjby.travel.leyou.fragment.PassChangeFragment;
import com.fjby.travel.leyou.fragment.PassRegisterFragment;
import com.fjby.travel.leyou.fragment.PassResetFragment;

public class PassWordActivity extends BaseActivity {

    public  static final int ResetPass=0;
    public  static final int ChangePass=1;
    public  static final int RegiserPass=2;
    public  static final String PassType="pass_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initToolbar(true, true);
        int type=   getIntent().getExtras().getInt(PassType,2);
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
        }

    }
    private void repalceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.password_framelayout,fragment);
        ft.commit();
    }
}
