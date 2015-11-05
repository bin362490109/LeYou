/**
 * Project Name:XPGSdkV4AppBase
 * File Name:FlushActivity.java
 * Package Name:com.gizwits.framework.activity
 * Date:2015-1-27 14:46:31
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.fjby.travel.leyou.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResAppStartup;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.utils.BitmapUtils;
import com.fjby.travel.leyou.utils.DialogUtils;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.NetworkUtils;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Lien on 14/12/16.
 * <p/>
 * 开机图界面
 *
 * @author Lien
 */
public class FlushActivity extends BaseActivity {
    private ImageView mLoading;
    private ImageView mImageView;
    private Bitmap bitmap = null;
    private boolean isStop = false;
    int i = 0;
    Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };


    private final Runnable task = new Runnable() {
        private int maxMargin = LeYouMyApplication.screenWidth;
        private int addMargin = maxMargin / 30;

        public void run() {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLoading.getLayoutParams();
            if (lp.leftMargin < maxMargin) {
                lp.leftMargin += addMargin;
            } else {
                lp.leftMargin = maxMargin;
            }
            //    LogUtil.e("lp.leftMargin==" + lp.leftMargin + " i=" + i++);
            mLoading.setLayoutParams(lp);
            if (!isStop)
                mHandler.postDelayed(this, 100);
        }
    };


    @Override
    protected void setView() {
        setContentView(R.layout.activity_flush);
    }

    @Override
    protected void initView() {
        //  Bitmap bitmap=BitmapFactory.decodeResource( getResources(),R.drawable.flush_bg);
        mLoading = (ImageView) findViewById(R.id.welcome_03_layout);
        mImageView = (ImageView) findViewById(R.id.welcome_bg);
        bitmap = BitmapUtils.decodeThumbBitmapForFile(getResources(), R.drawable.flush_bg, LeYouMyApplication.screenWidth, LeYouMyApplication.screenHeight);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doOther() {
        mHandler.post(task);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newFlush();
            }
        }, 3000);
    }


    private void newFlush() {
        //判断是否有账号登陆
        if (StringUtils.isEmpty(spf.getString("guid", ""))) {
            //未有账号登陆
            IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
            finish();
        } else {
            //已有账号登陆
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("req", "AppStartup");
            map.put("usertype", "1");
            map.put("system", "1");
            map.put("version", LeYouMyApplication.versionName);
            map.put("imei", LeYouMyApplication.imei);
            map.put("ip", LeYouMyApplication.ip);
            map.put("device", LeYouMyApplication.device);
            map.put("citycode", spf.getString("citycode", ""));
            LeYouMyApplication.mCashHhid=spf.getString("guid", "").trim();
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
                        Bundle bundle=new Bundle();
                        bundle.putString("vercode",resAppStartup.getVerCode());
                        IntentUtils.getInstance().startActivityWithBudle(FlushActivity.this, MainActivity.class,bundle);
                    } else {
                        ToastUtils.showLong(FlushActivity.this, resAppStartup.getStateMsg());
                        IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
                    }
                    finish();
                }


                @Override
                public void onError(Exception e) {
                    if(NetworkUtils.isNetworkConnected(FlushActivity.this)){
                        DialogUtils.mdialogShowOne(FlushActivity.this,"网络错误","服务器正在更新");
                    }else{
                       // DialogUtils.dialogShowOne(FlushActivity.this, "网络错误", "手机没有网络");
                        DialogUtils.mdialogShowOne(FlushActivity.this, "网络错误", "手机没有网络");

                    }

                }
            });

        }
    }
    private void oldFlush() {
        //判断是否有账号登陆
        if (StringUtils.isEmpty(spf.getString("guid", ""))) {
            //未有账号登陆
            IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
            finish();
        } else {
            //已有账号登陆
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("req", "UserLoginAccount");
            map.put("phone", spf.getString("name", ""));
            map.put("password", spf.getString("pass", ""));
            HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Gson gson = new Gson();
                    ResUser mResUser = gson.fromJson(response, ResUser.class);
                    if (mResUser.getStateCode() == 600) {
                        //// TODO: 2015/10/14 这个可能需要更改 （mUser可能要去掉）
                        LeYouMyApplication.mUser = mResUser.getUser();
                        LeYouMyApplication.mCashHhid = mResUser.getUser().getGuid();
                        IntentUtils.getInstance().startActivity(FlushActivity.this, MainActivity.class);
                    } else {
                        ToastUtils.showLong(FlushActivity.this, mResUser.getStateMsg());
                        IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
                    }
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    ToastUtils.showLong(FlushActivity.this, R.string.network_err);
                    finish();
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null && task != null) {
            mHandler.removeCallbacks(task);
            mHandler = null;
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        isStop = true;
        super.onDestroy();
    }

}
