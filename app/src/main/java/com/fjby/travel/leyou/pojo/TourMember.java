package com.fjby.travel.leyou.pojo;

public class TourMember {
	private String guid; // GUID
	private String groupGuid; // 旅游团GUID
	private String userGuid; // 用户GUID
	private String memberType; // 团员角色 MEMBER_TYPE
	private String addTime; // 登记日期
	private String stsTime; // 状态日期
	private String sts; // 状态 1 准备中 2 开始 0 结束
	private String longitude; // 经度
	private String latitude; // 维度

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGroupGuid() {
		return groupGuid;
	}

	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getStsTime() {
		return stsTime;
	}

	public void setStsTime(String stsTime) {
		this.stsTime = stsTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "TourMember [guid=" + guid + ", groupGuid=" + groupGuid
				+ ", userGuid=" + userGuid + ", memberType=" + memberType
				+ ", addTime=" + addTime + ", stsTime=" + stsTime + ", sts="
				+ sts + ", longitude=" + longitude + ", latitude=" + latitude
				+ "]";
	}

}
