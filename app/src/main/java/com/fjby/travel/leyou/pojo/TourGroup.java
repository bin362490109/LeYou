package com.fjby.travel.leyou.pojo;

public class TourGroup {
	private String guid;  //GUID
	private String productGuid;  //旅游产品GUID
	private String dateGuid;  // 旅游班次GUID
	private String startDate;  //出发日期
	private String curTravel;  //当前行程
	private String sts;  // 状态   1 准备中   2 开始  0 结束
	private String addTime;  //登记日期
  
  public String getGuid() {
    return guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }
public String getProductGuid() {
	return productGuid;
}
public void setProductGuid(String productGuid) {
	this.productGuid = productGuid;
}
public String getDateGuid() {
	return dateGuid;
}
public void setDateGuid(String dateGuid) {
	this.dateGuid = dateGuid;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getCurTravel() {
	return curTravel;
}
public void setCurTravel(String curTravel) {
	this.curTravel = curTravel;
}
public String getSts() {
	return sts;
}
public void setSts(String sts) {
	this.sts = sts;
}
public String getAddTime() {
	return addTime;
}
public void setAddTime(String addTime) {
	this.addTime = addTime;
}
@Override
public String toString() {
	return "TourGroupData [guid=" + guid + ", productGuid=" + productGuid
			+ ", dateGuid=" + dateGuid + ", startDate=" + startDate
			+ ", curTravel=" + curTravel + ", sts=" + sts + ", addTime="
			+ addTime + "]";
}

}
