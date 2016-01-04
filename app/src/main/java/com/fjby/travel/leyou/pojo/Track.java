package com.fjby.travel.leyou.pojo;

public class Track {
	private String guid;  //GUID账户ID(GUID)账户ID：采用唯一识别GUID保存
	private String userGuid;  //GUID账户ID(GUID)账户ID：采用唯一识别GUID保存
	private String setImei;  //手机设备识别码手机串号IMEIUUID(IMEI)
	private String setIccid;  //ICCID
	private String setSim;  //登陆验证码GUID格式
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getSetImei() {
		return setImei;
	}
	public void setSetImei(String setImei) {
		this.setImei = setImei;
	}
	public String getSetIccid() {
		return setIccid;
	}
	public void setSetIccid(String setIccid) {
		this.setIccid = setIccid;
	}
	public String getSetSim() {
		return setSim;
	}
	public void setSetSim(String setSim) {
		this.setSim = setSim;
	}
	@Override
	public String toString() {
		return "Track [guid=" + guid + ", userGuid=" + userGuid + ", setImei="
				+ setImei + ", setIccid=" + setIccid + ", setSim=" + setSim
				+ "]";
	}

}
