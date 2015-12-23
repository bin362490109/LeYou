package com.fjby.travel.leyou.pojo;

public class TourPlan {
	private String guid; // GUID
	private String groupGuid; // 旅游团GUID
	private String planDate; // 行程日期
	private String scheduleNo; // 行程次序
	private String tourMemo; // 行程简介
	private String cityCode; // 行程所在城市代码
	private String cityName; // 行程所在城市名称

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}


	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}



	public String getTourMemo() {
		return tourMemo;
	}

	public void setTourMemo(String tourMemo) {
		this.tourMemo = tourMemo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getGroupGuid() {
		return groupGuid;
	}

	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	@Override
	public String toString() {
		return "TourPlan [guid=" + guid + ", groupGuid=" + groupGuid
				+ ", planDate=" + planDate + ", scheduleNo=" + scheduleNo
				+ ", tourMemo=" + tourMemo + ", cityCode=" + cityCode
				+ ", cityName=" + cityName + "]";
	}


}
