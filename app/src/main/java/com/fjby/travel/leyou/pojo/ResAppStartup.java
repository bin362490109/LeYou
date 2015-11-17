package com.fjby.travel.leyou.pojo;

import java.util.List;

public class ResAppStartup extends Result {
    //后台进程信息
    private String appGuid;  //后台进程GUID
    private String rsaKey;   //RSA公共密钥
    //以下是版本信息
    private String verCode; //600—需要升级601-需要强制升级 700—已经是最新版本
    private String verInCode;
    private String verOutCode;
    private String verMsg;  //版本信息
    private String verDate;
    private String cityCode;

    //返回的用户信息
    private User user = null;

    //返回的广告信息
    private List<CityAd> cityAdList = null;

    //返回推荐景点
    private List<Tourist> touristList = null;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getVerInCode() {
        return verInCode;
    }

    public void setVerInCode(String verInCode) {
        this.verInCode = verInCode;
    }

    public String getVerOutCode() {
        return verOutCode;
    }

    public void setVerOutCode(String verOutCode) {
        this.verOutCode = verOutCode;
    }

    public String getVerMsg() {
        return verMsg;
    }

    public void setVerMsg(String verMsg) {
        this.verMsg = verMsg;
    }

    public String getVerDate() {
        return verDate;
    }

    public void setVerDate(String verDate) {
        this.verDate = verDate;
    }

    public List<CityAd> getCityAdList() {
        return cityAdList;
    }

    public void setCityAdList(List<CityAd> cityAdList) {
        this.cityAdList = cityAdList;
    }

    public List<Tourist> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<Tourist> touristList) {
        this.touristList = touristList;
    }

    public String getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
