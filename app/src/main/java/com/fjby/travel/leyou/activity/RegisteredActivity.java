package com.fjby.travel.leyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener{
    private final static int REG_SUCCESS = 0;
    private final static int TICK_TIME = 1;
    private final static int TOAST = -2;
    private Button mRegisetSureBtn;
    private EditText mRegisetPasswordEt;
    private Button mRegisetCodeBtn;
    private EditText mRegisetCodeEt;
    private EditText mRegisetphoneEt;
    private TextView mRegisetAgreeTV;
    private CheckBox mRegisetAgreeCb;
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
                        mRegisetCodeBtn.setText( "重新获取("+secondleft +")");

                    }
                    break;

                case REG_SUCCESS:
                    ToastUtils.showShort(RegisteredActivity.this, (String) msg.obj);
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("isRegister", true);
                    finish();
                    break;

                case TOAST:
                    ToastUtils.showShort(RegisteredActivity.this, (String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar(true, true);
        setToolbarTitle(R.string.register_label);

        mRegisetSureBtn = (Button) findViewById(R.id.regiset_sure_btn);
        mRegisetCodeBtn = (Button) findViewById(R.id.regiset_code_btn);
        mRegisetphoneEt = (EditText) findViewById(R.id.regiset_phone);
        mRegisetPasswordEt = (EditText) findViewById(R.id.regiset_password);
        mRegisetAgreeTV = (TextView) findViewById(R.id.regiset_agree_text);
        mRegisetAgreeCb = (CheckBox) findViewById(R.id.regiset_agree_checkbox);
        mRegisetPasswordEt = (EditText) findViewById(R.id.regiset_password);
        mRegisetCodeEt = (EditText) findViewById(R.id.regiset_code);
        mRegisetCodeBtn.setOnClickListener(this);
        mRegisetSureBtn.setOnClickListener(this);
        mRegisetAgreeTV.setOnClickListener(this);

    }


    /**
     * 处理发送验证码动作
     *
     * @param phone the phone
     */
    private void sendVerifyCode(final String phone) {
        // TODO Auto-generated method stub
        mRegisetCodeBtn.setEnabled(false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("req","reg");
        map.put("type","code");
        map.put("phone", phone);
        HttpUtil.sendVolleyRequestToString(map, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                if (response.trim().equals("501")) {
                    ToastUtils.showLong(RegisteredActivity.this, R.string.phone_is_same);
                } else if (response.trim().length() > 10) {
                    mRegisetCodeEt.setText(response.substring(11, 15));
                }

            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showLong(RegisteredActivity.this, R.string.network_err);
                timer.cancel();
                mRegisetCodeBtn.setEnabled(true);

            }
        });
        secondleft = 30;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
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
            case R.id.regiset_agree_text:
              ToastUtils.showLong(RegisteredActivity.this, "先同意注册协议才能注册");
                break;
            case R.id.regiset_sure_btn:
                if(checkSend()){
                   HashMap<String, String> map = new HashMap<String, String>();
                   map.put("req","reg");
                   map.put("type","reg");
                    map.put("phone",phone);
                   map.put("code",mRegisetCodeEt.getText().toString().trim());
                   map.put("password",mRegisetPasswordEt.getText().toString().trim());
                   HttpUtil.sendVolleyRequestToString(map, new HttpCallbackListener() {
                       @Override
                       public void onFinish(String response) {
                           CheckResult(response);
                       }
                       @Override
                       public void onError(Exception e) {
                           ToastUtils.showLong(RegisteredActivity.this, R.string.network_err);
                       }
                   });
               }
                break;
        }
    }
    private boolean checkSend(){
        if(!checkPhone()){
            return false;
        }
        if(StringUtils.isEmpty(mRegisetCodeEt.getText().toString())) {
            ToastUtils.showLong(RegisteredActivity.this, R.string.code_is_null);
            mRegisetCodeEt.requestFocus();
            return false;
        }
        if(StringUtils.isEmpty(mRegisetPasswordEt.getText().toString())){
            ToastUtils.showLong(RegisteredActivity.this, R.string.pass_is_null);
            mRegisetPasswordEt.requestFocus();
            return false;
        }else{
            if(mRegisetPasswordEt.getText().toString().trim().length()>6&&mRegisetPasswordEt.getText().toString().trim().length()<20){
                ToastUtils.showLong(RegisteredActivity.this, R.string.pass_length_err);
                mRegisetPasswordEt.requestFocus();
                return false;
            }
        }
        if(!mRegisetAgreeCb.isChecked()) {
            ToastUtils.showLong(RegisteredActivity.this, "请先勾选《注册协议》");
            return false;
        }
        return true;
    }
    private boolean checkPhone(){
        if(StringUtils.isEmpty(mRegisetphoneEt.getText().toString())){
            ToastUtils.showLong(RegisteredActivity.this, R.string.Phone_is_null);
            mRegisetphoneEt.requestFocus();
            return false;
        }
        if( mRegisetphoneEt.getText().toString().trim().length() != 11){
            ToastUtils.showLong(RegisteredActivity.this, R.string.Phone_length_err);
            mRegisetphoneEt.requestFocus();
            return false;
        }
        return true;
    }

    private void CheckResult(String msg) {
        if(msg.trim().equals("501")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.phone_is_same);
        }else if(msg.trim().equals("502")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.code_is_overtime);
        }else if(msg.trim().equals("503")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.code_is_err);
        }else if(msg.trim().equals("504")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.get_msg_code);
        }else if(msg.trim().equals("505")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.register_err);
        }else if(msg.trim().equals("600")){
            ToastUtils.showLong(RegisteredActivity.this, R.string.register_success);
        }else {
            ToastUtils.showLong(RegisteredActivity.this, msg);
        }

    }

    @Override
    protected void onDestroy() {

        if (handler!= null && timer != null) {
            timer.cancel();
            timer=null;
            handler = null;
        }
        super.onDestroy();

    }
}
