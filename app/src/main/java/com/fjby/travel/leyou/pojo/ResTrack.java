package com.fjby.travel.leyou.pojo;

import java.util.ArrayList;
import java.util.List;

public class ResTrack extends Result {
	private Track track = new Track();
	private List<Position> positionList = null;


	public List<Position> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<Position> positionList) {
		this.positionList = positionList;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	@Override
	public String toString() {
		return "ResTrack [track=" + track + ", positionList=" + positionList
				+ "]";
	}

}
