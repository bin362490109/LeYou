package com.fjby.travel.leyou.entity;

import java.util.Date;

public class ResUser {
    private String mGuid;   //Guid账户id
    private String mPhone;   //账户手机号
    private String mUserName;   //用户名
    private String mLoginPw;    //md5  加密的密码
    private String mRegDate;     //注册时间
    private String mImageCode;   //头像代码
    private String mImei;     //手机imei
    private String mShareLoginType;   //共享登录类型    1QQ  2微博
    private String mShateLoginId;   //共享登录账户
    private String mVerifyCode;   //登录验证码GUid格式
    private String mLastLoginTime;    //最后登录时间
    private String mIsValid;    //是否有效  1是   0否
    private String mUserType;    //用户类型   1游客   2导游   3旅游团   4旅行社    5景区    6旅游局

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String guid) {
        mGuid = guid;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getLoginPw() {
        return mLoginPw;
    }

    public void setLoginPw(String loginPw) {
        mLoginPw = loginPw;
    }

    public String getImageCode() {
        return mImageCode;
    }

    public void setImageCode(String imageCode) {
        mImageCode = imageCode;
    }

    public String getImei() {
        return mImei;
    }

    public void setImei(String imei) {
        mImei = imei;
    }

    public String getShareLoginType() {
        return mShareLoginType;
    }

    public void setShareLoginType(String shareLoginType) {
        mShareLoginType = shareLoginType;
    }

    public String getShateLoginId() {
        return mShateLoginId;
    }

    public void setShateLoginId(String shateLoginId) {
        mShateLoginId = shateLoginId;
    }

    public String getVerifyCode() {
        return mVerifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        mVerifyCode = verifyCode;
    }

    public String getIsValid() {
        return mIsValid;
    }

    public void setIsValid(String isValid) {
        mIsValid = isValid;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

    public String getRegDate() {
        return mRegDate;
    }

    public void setRegDate(String regDate) {
        mRegDate = regDate;
    }

    public String getLastLoginTime() {
        return mLastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        mLastLoginTime = lastLoginTime;
    }


}

