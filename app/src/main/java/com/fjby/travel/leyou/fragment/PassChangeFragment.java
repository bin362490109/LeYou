package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.BaseActivity;
import com.fjby.travel.leyou.activity.PassWordActivity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.SharePreferenceUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class PassChangeFragment extends Fragment implements View.OnClickListener {
    private Button mRegisetSureBtn;
    private EditText mChangeRenewpass;
    private EditText mChangeNewpass;
    private EditText mChangeOldpass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //// TODO: 2015/10/14   界面按钮需要更改
        View view = inflater.inflate(R.layout.fragment_changepass, container, false);
        ((PassWordActivity) getActivity()).setToolbarTitle(R.string.changepass_label);
        mRegisetSureBtn = (Button) view.findViewById(R.id.regiset_sure_btn);
        mChangeOldpass = (EditText) view.findViewById(R.id.old_password);
        mChangeNewpass = (EditText) view.findViewById(R.id.new_password);
        mChangeRenewpass = (EditText) view.findViewById(R.id.renew_password);
        mRegisetSureBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regiset_sure_btn:
                if (checkSend()) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("req", "UserChangePassword");
                    map.put("userid", LeYouMyApplication.mCashHhid);
                    map.put("oldpassword", mChangeOldpass.getText().toString().trim());
                    map.put("newpassword", mChangeNewpass.getText().toString().trim());
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
        LogUtil.e(mChangeOldpass.getText().toString()+"     "+mChangeNewpass.getText().toString()+"     "+ mChangeRenewpass.getText().toString()+"    "+!mChangeRenewpass.getText().toString().equals(mChangeNewpass.getText().toString()));
        if (StringUtils.isEmpty( mChangeOldpass.getText().toString())) {
            ToastUtils.showLong(getActivity(),"旧密码为空");
            mChangeOldpass.requestFocus();
            return false;
        }
        if (StringUtils.isEmpty( mChangeNewpass.getText().toString())) {
            ToastUtils.showLong(getActivity(), "新密码为空");
            mChangeNewpass.requestFocus();
            return false;
        }
        if (StringUtils.isEmpty( mChangeRenewpass.getText().toString())) {
            ToastUtils.showLong(getActivity(), "新密码为空");
            mChangeRenewpass.requestFocus();
            return false;
        }
        if (!mChangeRenewpass.getText().toString().equals(mChangeNewpass.getText().toString()) ) {
            ToastUtils.showLong(getActivity(), "新密码1和新密码2两次输入不一致");
            mChangeRenewpass.requestFocus();
            return false;
        }
        if ( mChangeOldpass.getText().toString().equals(mChangeNewpass.getText().toString())) {
            ToastUtils.showLong(getActivity(), "新旧两次密码不能一样");
            mChangeNewpass.requestFocus();
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
            ToastUtils.showLong(getActivity(), "修改成功，请重新登录");
            LeYouMyApplication.mUser = null;
           // LeYouMyApplication.mCashHhid = "-1";
             SharePreferenceUtil spf=SharePreferenceUtil.getInstance(getContext());
            spf.remove("hhid");
            spf.remove("pass");
            getActivity().finish();
          //  PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("hhid");
        }
    }
}
