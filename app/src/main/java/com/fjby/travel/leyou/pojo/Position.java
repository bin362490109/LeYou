package com.fjby.travel.leyou.pojo;

public class Position {
	private String latitude;//维度LATITUDE
	private String   longitude;        //经度LONGITUDE
	private String   uploadDate ;        //时间UPLOAD_DATE DATE
	private String   stationLac ;        //STATION_LAC  基站小区编号
	private String   stationCi ;        //STATION_CI  基站小区编号
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getStationCi() {
		return stationCi;
	}
	public void setStationCi(String stationCi) {
		this.stationCi = stationCi;
	}
	public String getStationLac() {
		return stationLac;
	}
	public void setStationLac(String stationLac) {
		this.stationLac = stationLac;
	}
	@Override
	public String toString() {
		return "Position [latitude=" + latitude + ", longitude=" + longitude
				+ ", uploadDate=" + uploadDate + ", stationLac=" + stationLac
				+ ", stationCi=" + stationCi + "]";
	}

}
