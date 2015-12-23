package com.fjby.travel.leyou.pojo;

import java.util.List;


public class ResTourGroupData extends Result{
	//返回的旅游团信息
	private TourGroup tourGroup=new TourGroup();

	//返回的旅游计划
	private List<TourPlan> tourPlanList=null;

	//返回旅游团成员
	private List<TourMember> tourMemberList=null;


	public List<TourPlan> getTourPlanList() {
		return tourPlanList;
	}

	public void setTourPlanList(List<TourPlan> tourPlanList) {
		this.tourPlanList = tourPlanList;
	}

	public List<TourMember> getTourMemberList() {
		return tourMemberList;
	}

	public void setTourMemberList(List<TourMember> tourMemberList) {
		this.tourMemberList = tourMemberList;
	}

	public TourGroup getTourGroup() {
		return tourGroup;
	}

	public void setTourGroup(TourGroup tourGroup) {
		this.tourGroup = tourGroup;
	}

	@Override
	public String toString() {
		return "ResTourGroupData [tourGroup=" + tourGroup + ", tourPlanList="
				+ tourPlanList + ", tourMemberList=" + tourMemberList + "]";
	}



}
