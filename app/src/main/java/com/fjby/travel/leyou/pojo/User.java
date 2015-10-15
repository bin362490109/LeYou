package com.fjby.travel.leyou.pojo;

public class User {
	private String guid;  //GUID账户ID(GUID)账户ID：采用唯一识别GUID保存
	//private String hhid;  //GUID账户ID(GUID)账户ID：采用唯一识别GUID保存
	private String phone;  //手机号码账户手机号
	private String userName;  //用户名名称
	private String loginPw;  //登录密码MD5加密
	private String regDate;  //注册时间
	private String imageCode;  //头像代码
	private String imei;  //手机设备识别码手机串号IMEIUUID(IMEI)
	private String shareLoginType;  //共享登录类型
	private String shareUserId;  //共享登录账号
	private String verifyCode;  //登陆验证码GUID格式
	private String lastLoginTime;  //最后登录时间
	private String isValid;  //是否有效状态代码：‘1’=是；‘0’：否
	private String userType;  //用户类型'1':游客；‘2’：导游；‘3':旅游团（群聊使用）；’4’:旅行社;‘5‘：景区；’6:‘：旅游局
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginPw() {
		return loginPw;
	}
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getShareLoginType() {
		return shareLoginType;
	}
	public void setShareLoginType(String shareLoginType) {
		this.shareLoginType = shareLoginType;
	}
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User{" +
				"guid='" + guid + '\'' +
				", phone='" + phone + '\'' +
				", userName='" + userName + '\'' +
				", loginPw='" + loginPw + '\'' +
				", regDate='" + regDate + '\'' +
				", imageCode='" + imageCode + '\'' +
				", imei='" + imei + '\'' +
				", shareLoginType='" + shareLoginType + '\'' +
				", shareUserId='" + shareUserId + '\'' +
				", verifyCode='" + verifyCode + '\'' +
				", lastLoginTime='" + lastLoginTime + '\'' +
				", isValid='" + isValid + '\'' +
				", userType='" + userType + '\'' +
				'}';
	}
}
