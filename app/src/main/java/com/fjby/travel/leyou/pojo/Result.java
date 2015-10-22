package com.fjby.travel.leyou.pojo;

public class Result {
  private int stateCode=600;  //600    ------- 成功， 否则其他返回
  private String stateMsg="";
  private String result="";
  
  public int getStateCode() {
    return stateCode;
  }
  public void setStateCode(int stateCode) {
    this.stateCode = stateCode;
  }
  public String getStateMsg() {
    return stateMsg;
  }
  public void setStateMsg(String stateMsg) {
    this.stateMsg = stateMsg;
  }
  public String getResult() {
    return result;
  }
  public void setResult(String result) {
    this.result = result;
  }
  
  
}
