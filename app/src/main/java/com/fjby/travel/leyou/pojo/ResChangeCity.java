package com.fjby.travel.leyou.pojo;

import java.util.ArrayList;
import java.util.List;

public class ResChangeCity extends Result{
  //返回的广告信息
  private List<CityAd> cityAdList=null;

  //返回推荐景点
  private List<Tourist> touristList=null;
  
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
  

}
