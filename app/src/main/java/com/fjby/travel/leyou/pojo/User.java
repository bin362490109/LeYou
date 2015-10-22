package com.fjby.travel.leyou.pojo;
/**
select '  private String '||ToMingmin(a.column_name)||';  //'||comments from user_tab_columns a,user_col_comments b 
where a.table_name='TBL_USER' and a.TABLE_NAME=b.table_name and a.COLUMN_NAME=b.column_name order by column_id

 *
 *
 */
public class User {
  private String guid;  //GUID账户ID(GUID)账户ID：采用唯一识别GUID保存
  private String phone;  //手机号码账户手机号
  private String userName;  //用户名名称
  private String userType;  //用户类型'1':游客；‘2’：导游；‘3':旅游团（群聊使用）；’4’:旅行社;‘5‘：景区；’6:‘：旅游局
  private String loginPw;  //登录密码MD5加密
  private String payPw;  //
  private String shareLoginType;  //共享登录类型
  private String shareUserId;  //共享登录账号
  private String regDate;  //注册时间
  private String email;  //电子邮箱
  private String sex;  //1 男 2 女
  private String belongCityCode;  //
  private String birthday;  //生日
  private String imageCode;  //头像代码
  private String isValid;  //是否有效状态代码：‘1’=是；‘0’：否
  private String imei;  //手机设备识别码手机串号IMEIUUID(IMEI)
  private String cityCode;  //
  private String verifyCode;  //登陆验证码GUID格式
  private String lastLoginTime;  //最后登录时间
  
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
  public String getUserType() {
    return userType;
  }
  public void setUserType(String userType) {
    this.userType = userType;
  }
  public String getLoginPw() {
    return loginPw;
  }
  public void setLoginPw(String loginPw) {
    this.loginPw = loginPw;
  }
  public String getPayPw() {
    return payPw;
  }
  public void setPayPw(String payPw) {
    this.payPw = payPw;
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
  public String getRegDate() {
    return regDate;
  }
  public void setRegDate(String regDate) {
    this.regDate = regDate;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getSex() {
    return sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getBelongCityCode() {
    return belongCityCode;
  }
  public void setBelongCityCode(String belongCityCode) {
    this.belongCityCode = belongCityCode;
  }
  public String getBirthday() {
    return birthday;
  }
  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
  public String getImageCode() {
    return imageCode;
  }
  public void setImageCode(String imageCode) {
    this.imageCode = imageCode;
  }
  public String getIsValid() {
    return isValid;
  }
  public void setIsValid(String isValid) {
    this.isValid = isValid;
  }
  public String getImei() {
    return imei;
  }
  public void setImei(String imei) {
    this.imei = imei;
  }
  public String getCityCode() {
    return cityCode;
  }
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
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

  @Override
  public String toString() {
    return "User{" +
            "guid='" + guid + '\'' +
            ", phone='" + phone + '\'' +
            ", userName='" + userName + '\'' +
            ", userType='" + userType + '\'' +
            ", loginPw='" + loginPw + '\'' +
            ", payPw='" + payPw + '\'' +
            ", shareLoginType='" + shareLoginType + '\'' +
            ", shareUserId='" + shareUserId + '\'' +
            ", regDate='" + regDate + '\'' +
            ", email='" + email + '\'' +
            ", sex='" + sex + '\'' +
            ", belongCityCode='" + belongCityCode + '\'' +
            ", birthday='" + birthday + '\'' +
            ", imageCode='" + imageCode + '\'' +
            ", isValid='" + isValid + '\'' +
            ", imei='" + imei + '\'' +
            ", cityCode='" + cityCode + '\'' +
            ", verifyCode='" + verifyCode + '\'' +
            ", lastLoginTime='" + lastLoginTime + '\'' +
            '}';
  }
}
