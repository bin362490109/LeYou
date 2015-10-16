package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.PassWordActivity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.pojo.User;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.RoundedImageView;
import com.google.gson.Gson;

import java.util.HashMap;

public class UpdateInfoFragment extends Fragment implements View.OnClickListener {
    private Button updateinfobutton;
    private TextView updateinfosharename;
    private TextView updateinfosharetype;
    private TextView updateinfotype;
    private TextView updateinfodate;
    private TextView updateinfophone;
    private TextView updateinfoname;
    private RoundedImageView updateinfoimage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //// TODO: 2015/10/14   界面按钮需要更改
        View view = inflater.inflate(R.layout.fragment_updateinfo, container, false);
        ((PassWordActivity) getActivity()).setToolbarTitle(R.string.updateinfo_label);
        updateinfobutton = (Button) view.findViewById(R.id.updateinfo_button);
        updateinfosharename = (TextView) view.findViewById(R.id.updateinfo_sharename);
        updateinfosharetype = (TextView) view.findViewById(R.id.updateinfo_sharetype);
        updateinfotype = (TextView) view.findViewById(R.id.updateinfo_type);
        updateinfodate = (TextView) view.findViewById(R.id.updateinfo_date);
        updateinfophone = (TextView) view.findViewById(R.id.updateinfo_phone);
        updateinfoname = (TextView) view.findViewById(R.id.updateinfo_name);
        updateinfoimage = (RoundedImageView) view.findViewById(R.id.updateinfo_image);

        updateinfobutton.setOnClickListener(this);
        updateinfoname.setOnClickListener(this);
        updateinfoimage.setOnClickListener(this);
        initDate();
        return view;
    }

    private void initDate() {
        if(LeYouMyApplication.mUser!=null){
            User user=LeYouMyApplication.mUser;
            updateinfosharename.setText(user.getShareUserId());
            updateinfosharetype.setText(user.getShareLoginType());
            updateinfotype.setText(user.getUserType());
            updateinfodate.setText(user.getRegDate());
            updateinfophone.setText(user.getPhone());
            updateinfoname.setText(user.getUserName());
       //   updateinfoimage.setText(user.getUserName());


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateinfo_button:
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("req", "UserUpdateInfo");
                map.put("userid", LeYouMyApplication.mCashHhid);
              //  map.put("imagecode", LeYouMyApplication.mUser.getImageCode());
                map.put("username", updateinfoname.getText().toString().trim());
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
                break;
            case R.id.updateinfo_name:
                new MaterialDialog.Builder(getActivity())
                        .title("修改用户名")
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    updateinfoname.setText(input.toString());
                                } else {
                                    ToastUtils.showLong(getActivity(),"不能为空");
                                }
                            }

                        }).show();
                break;
            case R.id.updateinfo_image:
                ToastUtils.show(getActivity(), "暂时不支持换图像", 0);
                break;
        }
    }


    private void CheckResult(String msg) {
        Gson gson = new Gson();
        ResUser mResUser = gson.fromJson(msg, ResUser.class);
        if (mResUser.getStateCode() != 600) {
            ToastUtils.showLong(getActivity(), mResUser.getStateMsg());
        } else {
            ToastUtils.showLong(getActivity(), "修改成功");
            LeYouMyApplication.mUser = mResUser.getUser();
        }
    }
}
