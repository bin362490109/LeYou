package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.PassWordActivity;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PassResetFragment extends Fragment implements View.OnClickListener {
    private final static int REG_SUCCESS = 0;
    private final static int TICK_TIME = 1;
    private final static int TOAST = -2;
    private Button mRegisetSureBtn;
    private EditText mRegisetPasswordEt;
    private Button mRegisetCodeBtn;
    private EditText mRegisetCodeEt;
    private EditText mRegisetphoneEt;
    /**
     * 验证码重发倒计时
     */
    int secondleft = 60;
    /**
     * The timer.
     */
    Timer timer;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TICK_TIME:
                    secondleft--;
                    if (secondleft <= 0) {
                        timer.cancel();
                        mRegisetCodeBtn.setEnabled(true);
                        mRegisetCodeBtn.setText("重新发送");
                    } else {
                        mRegisetCodeBtn.setText("重新获取(" + secondleft + ")");

                    }
                    break;
                case REG_SUCCESS:
                    ToastUtils.showShort(getActivity(), (String) msg.obj);
                    break;

                case TOAST:
                    ToastUtils.showShort(getActivity(), (String) msg.obj);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //// TODO: 2015/10/14   界面按钮需要更改
        View view = inflater.inflate(R.layout.fragment_resetpass, container, false);
        ((PassWordActivity) getActivity()).setToolbarTitle(R.string.resetpass_label);
        mRegisetSureBtn = (Button) view.findViewById(R.id.regiset_sure_btn);
        mRegisetCodeBtn = (Button) view.findViewById(R.id.regiset_code_btn);
        mRegisetphoneEt = (EditText) view.findViewById(R.id.regiset_phone);
        mRegisetPasswordEt = (EditText) view.findViewById(R.id.regiset_password);
        mRegisetCodeEt = (EditText) view.findViewById(R.id.regiset_code);
        mRegisetCodeBtn.setOnClickListener(this);
        mRegisetSureBtn.setOnClickListener(this);
        return view;
    }
    /**
     * 处理发送验证码动作
     *
     * @param phone the phone
     */
    private void sendVerifyCode(final String phone) {
        mRegisetCodeBtn.setEnabled(false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("req", "reg");
        map.put("phone", phone);
        HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                ResUser mResUser = gson.fromJson(response, ResUser.class);
                if (mResUser.getStateCode() == 600) {
                    mRegisetCodeEt.setText(mResUser.getStateMsg().substring(11, 15));
                } else {
                    ToastUtils.showLong(getActivity(), mResUser.getStateMsg());
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showLong(getActivity(), R.string.network_err);
                timer.cancel();
                mRegisetCodeBtn.setEnabled(true);
            }
        });
        secondleft = 30;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TICK_TIME);
            }
        }, 1000, 1000);
    }

    @Override
    public void onClick(View v) {
        String phone = mRegisetphoneEt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.regiset_code_btn:
                if (checkPhone()) {
                    sendVerifyCode(phone);
                }
                break;
            case R.id.regiset_sure_btn:
                if (checkSend()) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("req", "UserResetPassword");
                    map.put("phone", phone);
                    map.put("usertype", "1");
                    map.put("smscode", mRegisetCodeEt.getText().toString().trim());
                    map.put("password", mRegisetPasswordEt.getText().toString().trim());
                    HttpUtil.sendVolleyRequesttoParam(map, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            CheckResult(response);
                        }

                        @Override
                        public void onError(Exception e) {
                            ToastUtils.showLong(getActivity(), R.string.network_err);
                        }
                    });
                }
                break;
        }
    }

    private boolean checkSend() {
        if (!checkPhone()) {
            return false;
        }
        if (StringUtils.isEmpty(mRegisetCodeEt.getText().toString())) {
            ToastUtils.showLong(getActivity(), R.string.code_is_null);
            mRegisetCodeEt.requestFocus();
            return false;
        }
        if (StringUtils.isEmpty(mRegisetPasswordEt.getText().toString())) {
            ToastUtils.showLong(getActivity(), R.string.pass_is_null);
            mRegisetPasswordEt.requestFocus();
            return false;
        } else {
            if (mRegisetPasswordEt.getText().toString().trim().length() > 6 && mRegisetPasswordEt.getText().toString().trim().length() < 20) {
                ToastUtils.showLong(getActivity(), R.string.pass_length_err);
                mRegisetPasswordEt.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean checkPhone() {
        if (StringUtils.isEmpty(mRegisetphoneEt.getText().toString())) {
            ToastUtils.showLong(getActivity(), R.string.Phone_is_null);
            mRegisetphoneEt.requestFocus();
            return false;
        }
        if (mRegisetphoneEt.getText().toString().trim().length() != 11) {
            ToastUtils.showLong(getActivity(), R.string.Phone_length_err);
            mRegisetphoneEt.requestFocus();
            return false;
        }
        return true;
    }

    private void CheckResult(String msg) {
        Gson gson = new Gson();
        ResUser mResUser = gson.fromJson(msg, ResUser.class);
        if(mResUser.getStateCode()!=600) {
            ToastUtils.showLong(getActivity(), mResUser.getStateMsg());
        }else{
            ToastUtils.showLong(getActivity(), "重置成功");
        }
    }

    @Override
    public void onDestroy() {

        if (handler != null && timer != null) {
            timer.cancel();
            timer = null;
            handler = null;
        }
        super.onDestroy();

    }
}
