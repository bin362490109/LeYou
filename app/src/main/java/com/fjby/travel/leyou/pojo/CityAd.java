package com.fjby.travel.leyou.pojo;

public class CityAd {
  private String guid;  //GUID
  private String city;  //旅游城市代码
  private String adType;  //类型1-新闻资讯，2-优惠活动，3-图片广告
  private String adTitle;  //标题
  private String adAbstract;  //简介
  private String url;  //外链接地址
  private String imgurl;  //图文URL
  private String visitCount;  //访问量
  private String isShow;  //是否显示
  private String orderNo;  //排序
  private String addTime;  //增加时间
  public String getGuid() {
    return guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getAdType() {
    return adType;
  }
  public void setAdType(String adType) {
    this.adType = adType;
  }
  public String getAdTitle() {
    return adTitle;
  }
  public void setAdTitle(String adTitle) {
    this.adTitle = adTitle;
  }
  public String getAdAbstract() {
    return adAbstract;
  }
  public void setAdAbstract(String adAbstract) {
    this.adAbstract = adAbstract;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getImgurl() {
    return imgurl;
  }
  public void setImgurl(String imgurl) {
    this.imgurl = imgurl;
  }
  public String getVisitCount() {
    return visitCount;
  }
  public void setVisitCount(String visitCount) {
    this.visitCount = visitCount;
  }
  public String getIsShow() {
    return isShow;
  }
  public void setIsShow(String isShow) {
    this.isShow = isShow;
  }
  public String getOrderNo() {
    return orderNo;
  }
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  public String getAddTime() {
    return addTime;
  }
  public void setAddTime(String addTime) {
    this.addTime = addTime;
  }

}
