package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.utils.DialogUtils;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

/**
 * Created by abin on 2015/9/17.
 */
public class MySettingActivity extends BaseActivity {

    @Override
    protected void setView() {
        setContentView(R.layout.activity_mysetting);
        initToolbar(true, true);
        setToolbarTitle(R.string.mysetting_title);
    }

    @Override
    protected void initView() {
        TextView view=(TextView)findViewById(R.id.quit_account);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.mdialogShowQuit(MySettingActivity.this,"退出当前账号","是否退出当前账号并重新登陆");
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {

    }

}
