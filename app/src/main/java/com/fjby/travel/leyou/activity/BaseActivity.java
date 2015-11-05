/**
 * Project Name:XPGSdkV4AppBase
 * File Name:BaseActivity.java
 * Package Name:com.gizwits.framework.activity
 * Date:2015-1-27 11:32:52
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

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.SharePreferenceUtil;


/**
 * 所有activity的基类。该基类实现了XPGWifiDeviceListener和XPGWifiSDKListener两个监听器，并提供全局的回调方法。
 * .
 *
 * @author Lien Li
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean isExit = false;
    protected SharePreferenceUtil spf;
    protected Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeYouMyApplication.addActivity(this);
        LogUtil.e("base activity=====" + LeYouMyApplication.Size());
        spf = SharePreferenceUtil.getInstance(getApplicationContext());
        setView();
        initView();
        setListener();
        doOther();
    }

    protected abstract void setView();
    protected abstract void initView();
    protected abstract void setListener();
    protected abstract void doOther();

    protected void initToolbar(boolean showToolbar, boolean showNav) {
        if (setToolbarIsShow(showToolbar)) {
            setToolbarHomeEnabled(showNav);
        }
    }

    private boolean setToolbarIsShow(boolean isShow) {
        if (isShow) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle("");
            mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            setSupportActionBar(mToolbar);
        }
        return isShow;
    }


    public void setToolbarTitle(int resid) {
        mToolbarTitle.setText(resid);
    }


    public void setToolbarNavigationIcon(int resid) {
        mToolbar.setNavigationIcon(resid);
    }

    public void setToolbarShow(boolean show) {
        if (show) {
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    private void setToolbarHomeEnabled(boolean isShow) {
        if (isShow) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        finish();
                    } else {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        }
    }

    protected void repalceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_base, fragment);
        ft.commit();
    }

    public void repalceFragmentWithTag(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_base, fragment);
        ft.addToBackStack("tag");
        ft.commit();
    }


    public void onResume() {
        super.onResume();
        // 每次返回activity都要注册一次sdk监听器，保证sdk状态能正确回调
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）  
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。  
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点  
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeYouMyApplication.removeActivity(this);
    }
}
