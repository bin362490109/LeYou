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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.utils.BitmapUtils;
import com.fjby.travel.leyou.utils.IntentUtils;
import com.fjby.travel.leyou.utils.LogUtil;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flush);
        bitmap = BitmapUtils.decodeThumbBitmapForFile(getResources(), R.drawable.flush_bg, LeYouMyApplication.screenWidth, LeYouMyApplication.screenHeight);
        //  Bitmap bitmap=BitmapFactory.decodeResource( getResources(),R.drawable.flush_bg);
        mLoading = (ImageView) findViewById(R.id.welcome_03_layout);
        mImageView = (ImageView) findViewById(R.id.welcome_bg);
        mImageView.setImageBitmap(bitmap);
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
        if (StringUtils.isEmpty(spf.getString("hhid", ""))) {
            //未有账号登陆
            IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
            finish();
        } else {
            //已有账号登陆
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("req", "UserLoginAccount");
            map.put("phone", spf.getString("hhid", ""));
            map.put("password", spf.getString("pass", ""));
            HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Gson gson = new Gson();
                    ResUser mResUser = gson.fromJson(response, ResUser.class);
                    if (mResUser.getStateCode() == 600) {
                        //// TODO: 2015/10/14 这个可能需要更改 （mUser可能要去掉）
                /*        LogUtil.e("mUser=" + mResUser.getUser().toString());
                        LogUtil.e("response=" + response);*/
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
