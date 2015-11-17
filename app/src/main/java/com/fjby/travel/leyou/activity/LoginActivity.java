package com.fjby.travel.leyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fjby.travel.baidulibrary.listener.MyLocationListener;
import com.fjby.travel.baidulibrary.utils.LocationUtils;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResAppStartup;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.soexample.utils.AccountUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private EditText mNameEditText;
    private EditText mPassEditText;
    private TextView mRegisetTv;
    private TextView mForgetTv;
    private TextView mLoginQqTv;
    private TextView mLoginWecharTv;
    private TextView mLoginWeiboTv;
    private Button mLoginBtn;
    private MyAccountInfoListen myAccountInfoListen;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_login);
        initToolbar(true, true);
        setToolbarTitle(R.string.login_label);
    }

    @Override
    protected void initView() {
        mRegisetTv = (TextView) findViewById(R.id.btnRegiset);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);
        mNameEditText = (EditText) findViewById(R.id.login_name);
        mPassEditText = (EditText) findViewById(R.id.login_password);
        mForgetTv = (TextView) findViewById(R.id.btnForget);
        mLoginQqTv = (TextView) findViewById(R.id.login_QQ);
        mLoginWecharTv = (TextView) findViewById(R.id.login_wechat);
        mLoginWeiboTv = (TextView) findViewById(R.id.login_weibo);
        myAccountInfoListen = new MyAccountInfoListen();
    }

    @Override
    protected void setListener() {
        mRegisetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(PassWordActivity.PassType, PassWordActivity.RegiserPass);
                IntentUtils.getInstance().startActivityWithBudle(LoginActivity.this, PassWordActivity.class, bundle);
            }
        });
        mForgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(PassWordActivity.PassType, PassWordActivity.ResetPass);
                IntentUtils.getInstance().startActivityWithBudle(LoginActivity.this, PassWordActivity.class, bundle);
            }
        });
        mLoginQqTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountUtils.login(LoginActivity.this, SHARE_MEDIA.QQ, myAccountInfoListen);
            }
        });
        mLoginWecharTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountUtils.login(LoginActivity.this, SHARE_MEDIA.WEIXIN, myAccountInfoListen);
            }
        });
        mLoginWeiboTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountUtils.login(LoginActivity.this, SHARE_MEDIA.SINA, myAccountInfoListen);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSend()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("req", "UserLoginAccount");
                    map.put("phone", mNameEditText.getText().toString().trim());
                    map.put("password", mPassEditText.getText().toString().trim());
                    map.put("usertype", "1");
                    map.put("imei", LeYouMyApplication.imei);
                    HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
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

    @Override
    protected void doOther() {
        if (StringUtils.isEmpty(spf.getString("city", ""))) {
            LocationUtils.getLocation(LoginActivity.this, new MyLocationListener.OnLocationListener() {
                @Override
                public void onLocation(double x, double y, String addr) {
                    spf.setString("city", addr);
                }
            });
        }
        //TODO 添加这一步才可以返回界面,而且必须自己的appid
        AccountUtils.addQZoneQQPlatform(LoginActivity.this);
        AccountUtils.addSinaPlatform();
        AccountUtils.addUMWXPlatform(LoginActivity.this);
    }

    private class MyAccountInfoListen implements AccountUtils.AccountInfoListener {

        @Override
        public void getAccountInfo(Map<String, Object> info) {
            HashMap<String, String> map = new HashMap<>();
            map.put("req", "UserAuthoLogin");
            map.put("phone", "");
            map.put("password", "");
            map.put("usertype", "1");
            map.put("imei", LeYouMyApplication.imei);
            map.put("username", info.get("screen_name").toString());
            map.put("imagecode", info.get("profile_image_url").toString());
            map.put("shareusertype", info.get("shareusertype").toString());
            map.put("shareuserid", info.get("shareuserid").toString());
            HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
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

    private boolean checkSend() {
        if (StringUtils.isEmpty(mNameEditText.getText().toString())) {
            ToastUtils.showLong(LoginActivity.this, R.string.name_is_null);
            mNameEditText.requestFocus();
            return false;
        }
        if (StringUtils.isEmpty(mPassEditText.getText().toString())) {
            ToastUtils.showLong(LoginActivity.this, R.string.pass_is_null);
            mPassEditText.requestFocus();
            return false;
        }
        return true;
    }

    private void CheckResult(String msg) {
        Gson gson = new Gson();
        ResUser mResUser = gson.fromJson(msg, ResUser.class);
        if (mResUser.getStateCode() == 600) {
            spf.setString("guid", mResUser.getUser().getGuid());
            //已有账号登陆
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("req", "AppStartup");
            map.put("usertype", "1");
            map.put("system", "1");
            map.put("version", LeYouMyApplication.versionName);
            map.put("imei", LeYouMyApplication.imei);
            map.put("ip", LeYouMyApplication.ip);
            map.put("device", LeYouMyApplication.device);
            String cityCode = spf.getString("citycode", "");
            if (StringUtils.isEmpty(cityCode)) {
                map.put("citycode", cityCode);
            } else {
                map.put("city", spf.getString("city", ""));
            }
            LeYouMyApplication.mCashHhid = spf.getString("guid", "").trim();
            HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Gson gson = new Gson();
                    ResAppStartup resAppStartup = gson.fromJson(response, ResAppStartup.class);
                    if (resAppStartup.getStateCode() == 600) {
                        //// TODO: 2015/10/14 这个可能需要更改 （mUser可能要去掉）
                        LeYouMyApplication.mUser = resAppStartup.getUser();
                        LeYouMyApplication.mCashHhid = resAppStartup.getUser().getGuid();
                        LeYouMyApplication.cityAdList = resAppStartup.getCityAdList();
                        LeYouMyApplication.touristList = resAppStartup.getTouristList();
                        spf.setString("citycode", resAppStartup.getCityCode());
                        Bundle bundle = new Bundle();
                        bundle.putString("vercode", resAppStartup.getVerCode());
                        IntentUtils.getInstance().startActivityWithBudle(LoginActivity.this, MainActivity.class, bundle);
                        finish();
                    } else {
                        ToastUtils.showLong(LoginActivity.this, resAppStartup.getStateMsg());
                    }
                }

                @Override
                public void onError(Exception e) {
                }
            });
        } else {
            ToastUtils.showLong(LoginActivity.this, mResUser.getStateMsg());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**新浪使用SSO授权必须添加如下代码  否则删退 */
        UMSsoHandler ssoHandler = AccountUtils.mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}