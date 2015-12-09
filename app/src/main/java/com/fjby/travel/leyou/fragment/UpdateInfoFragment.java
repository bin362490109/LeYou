package com.fjby.travel.leyou.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.PassWordActivity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.ResUser;
import com.fjby.travel.leyou.pojo.User;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.widget.RoundedImageView;
import com.google.gson.Gson;

import java.util.HashMap;

public class UpdateInfoFragment extends Fragment implements View.OnClickListener {
    private Button updateInfoButton;
    private TextView updateInfoShareName;
    private TextView updateInfoShareType;
    private TextView updateInfoDate;
    private TextView updateInfoEmail;
    private TextView updateInfoBelongCity;
    private RadioGroup updateInfoSex;
    private TextView updateInfoBirthday;
    private TextView updateInfoName;
    private RoundedImageView updateInfoImageCode;
    private MaterialDialog.Builder mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //// TODO: 2015/10/14   界面按钮需要更改
        View view = inflater.inflate(R.layout.fragment_updateinfo, container, false);
        ((PassWordActivity) getActivity()).setToolbarTitle(R.string.updateinfo_label);
        updateInfoButton = (Button) view.findViewById(R.id.updateinfo_button);
        updateInfoShareName = (TextView) view.findViewById(R.id.updateinfo_sharename);
        updateInfoShareType = (TextView) view.findViewById(R.id.updateinfo_sharetype);
        updateInfoDate = (TextView) view.findViewById(R.id.updateinfo_date);
        updateInfoName = (TextView) view.findViewById(R.id.updateinfo_name);
        updateInfoEmail = (TextView) view.findViewById(R.id.updateinfo_email);
        updateInfoBelongCity = (TextView) view.findViewById(R.id.updateinfo_city);
        updateInfoSex = (RadioGroup) view.findViewById(R.id.updateinfo_sex);
        updateInfoBirthday = (TextView) view.findViewById(R.id.updateinfo_birthday);
        updateInfoImageCode = (RoundedImageView) view.findViewById(R.id.updateinfo_image);
        mDialog= new MaterialDialog.Builder(getActivity());
        mDialog.theme(Theme.LIGHT);
        updateInfoButton.setOnClickListener(this);
        updateInfoName.setOnClickListener(this);
        updateInfoEmail.setOnClickListener(this);
        updateInfoBelongCity.setOnClickListener(this);
        updateInfoSex.setOnClickListener(this);
        updateInfoBirthday.setOnClickListener(this);
        updateInfoImageCode.setOnClickListener(this);
        initDate();
        return view;
    }

    private void initDate() {
        if(LeYouMyApplication.mUser!=null){
            User user=LeYouMyApplication.mUser;
            updateInfoShareName.setText(user.getShareUserId());
            updateInfoShareType.setText(user.getShareLoginType());
            updateInfoDate.setText(user.getRegDate());
            updateInfoName.setText(user.getUserName());
            updateInfoEmail.setText(user.getEmail());
            LogUtil.e("user.getSex()======================"+user.getSex()+"      "+user.toString());
            if(user.getSex()!=null&&user.getSex().equals("2")){
                updateInfoSex.check(R.id.updateinfo_sex_nv);
            }else{
                updateInfoSex.check(R.id.updateinfo_sex_nan);
            }

            updateInfoBelongCity.setText(user.getBelongCityCode());
            updateInfoBirthday.setText(user.getBirthday());
            if (!TextUtils.isEmpty(user.getImageCode())) {
                HttpUtil.testImageLoad(getActivity(),LeYouMyApplication.mUser.getImageCode(), updateInfoImageCode, R.drawable.author, R.drawable.author);
            }
       //   updateInfoImageCode.setText(user.getUserName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateinfo_button:
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("req", "UserUpdateInfo");
                map.put("imagecode", LeYouMyApplication.mUser.getImageCode()!=null? LeYouMyApplication.mUser.getImageCode():"");
                map.put("username", updateInfoName.getText().toString().trim());
                map.put("email", updateInfoEmail.getText().toString().trim());
                map.put("sex", updateInfoSex.getCheckedRadioButtonId()==R.id.updateinfo_sex_nan?"1":"2");
                map.put("belongcitycode", updateInfoBelongCity.getText().toString().trim());
                map.put("birthday", updateInfoBirthday.getText().toString().trim());
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
                mDialog.title("修改用户名")
                        .input("请输入新的用户名", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    updateInfoName.setText(input.toString());
                                } else {
                                    ToastUtils.showLong(getActivity(), "不能为空");
                                }
                            }
                        }).show();
                break;
            case R.id.updateinfo_image:
                ToastUtils.show(getActivity(), "暂时不支持换图像", 0);
                break;
            case R.id.updateinfo_city:
                mDialog.title("所属城市") .input("请输入你所在的城市", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            updateInfoBelongCity.setText(input.toString());
                        } else {
                            ToastUtils.showLong(getActivity(), "不能为空");
                        }
                    }
                }).show();
                break;
            case R.id.updateinfo_birthday:
                mDialog.title("出生日期").input("请输入你的出生日期", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            updateInfoBirthday.setText(input.toString());
                        } else {
                            ToastUtils.showLong(getActivity(),"不能为空");
                        }
                    }
                }).show();
                break;
            case R.id.updateinfo_email:
                mDialog.title("邮箱地址").input("请输入你的邮箱地址", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            updateInfoEmail.setText(input.toString());
                        } else {
                            ToastUtils.showLong(getActivity(), "不能为空");
                        }
                    }
                }).show();
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
