package com.fjby.travel.leyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

import java.util.HashMap;

/**
 * Created by abin on 2015/9/15.
 */
public class LoginActivity extends BaseActivity {
    private EditText mNameEditText;
    private EditText mPassEditText;
    private TextView mRegisetTv;
    private TextView mForgetTv;
    private TextView mLoginQqTv;
    private TextView mLoginWecharTv;
    private TextView mLoginWeiboTv;
    private Button mLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar(true,true);
        setToolbarTitle(R.string.login_label);
        mRegisetTv = (TextView) findViewById(R.id.btnRegiset);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);
        mNameEditText = (EditText) findViewById(R.id.login_name);
        mPassEditText = (EditText) findViewById(R.id.login_password);
        mForgetTv = (TextView) findViewById(R.id.btnForget);
        mLoginQqTv = (TextView) findViewById(R.id.login_QQ);
        mLoginWecharTv = (TextView) findViewById(R.id.login_wechat);
        mLoginWeiboTv = (TextView) findViewById(R.id.login_weibo);
        mRegisetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               IntentUtils.getInstance().startActivity(LoginActivity.this, RegisteredActivity.class);
            }
        });
        mForgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtils.showLong(LoginActivity.this, R.string.button_forget);
            }
        });
        mLoginQqTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtils.showLong(LoginActivity.this, R.string.QQ);
            }
        });
        mLoginWecharTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtils.showLong(LoginActivity.this, R.string.wechat);
            }
        });
        mLoginWeiboTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUtils.showLong(LoginActivity.this,R.string.weibo);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSend()){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name",mNameEditText.getText().toString().trim());
                    map.put("req","login");
                    map.put("pass", mPassEditText.getText().toString().trim());
                    HttpUtil.sendVolleyRequestToString(map, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            CheckResult(response);
                        }

                        @Override
                        public void onError(Exception e) {
                            ToastUtils.showLong(LoginActivity.this, R.string.network_err);
                        }
                    });
                }

            }
        });
    }

    private boolean checkSend(){
        if(StringUtils.isEmpty(mNameEditText.getText().toString())){
            ToastUtils.showLong(LoginActivity.this, R.string.name_is_null);
            mNameEditText.requestFocus();
            return false;
        }
        if(StringUtils.isEmpty(mPassEditText.getText().toString())) {
            ToastUtils.showLong(LoginActivity.this, R.string.pass_is_null);
            mPassEditText.requestFocus();
            return false;
        }
        return true;
    }

    private void CheckResult(String msg) {
        ToastUtils.showLong(LoginActivity.this,msg);
        if(msg.trim().equals("502")){
            ToastUtils.showLong(LoginActivity.this, R.string.account_err);
        }else if(msg.trim().equals("503")){
            ToastUtils.showLong(LoginActivity.this, R.string.account_is_valid);
        }else {
           spf.setString("name", mNameEditText.getText().toString().trim());
            spf.setString("pass",mPassEditText.getText().toString().trim());
            Bundle bundle=new Bundle();
            bundle.putString("user",msg);
            IntentUtils.getInstance().startActivityWithBudle(LoginActivity.this, MainActivity.class, bundle);
            finish();
        }

    }
}