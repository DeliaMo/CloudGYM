package com.thePosition.model;

import java.io.Serializable;

public class ThePositionVO implements Serializable{
	private Integer positionNo;
	private String positionName;
	public Integer getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(Integer positionNo) {
		this.positionNo = positionNo;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
