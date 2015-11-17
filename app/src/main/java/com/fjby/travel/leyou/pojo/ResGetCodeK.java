package com.fjby.travel.leyou.pojo;

import java.util.List;

public class ResGetCodeK extends Result{
	private List<CityCode> cityCodeLsit=null;;
	private List<ProvinceCode> provinceCodeList=null;
	public List<CityCode> getCityCodeLsit() {
		return cityCodeLsit;
	}
	public void setCityCodeLsit(List<CityCode> cityCodeLsit) {
		this.cityCodeLsit = cityCodeLsit;
	}
	public List<ProvinceCode> getProvinceCodeList() {
		return provinceCodeList;
	}
	public void setProvinceCodeList(List<ProvinceCode> provinceCodeList) {
		this.provinceCodeList = provinceCodeList;
	};

  
  
  

}
