package com.coachMenuList.model;

import java.io.Serializable;

public class CoachMenuListVO implements Serializable{
	private Integer menuNo;
	private Integer menuID;
	private Integer videoID;
	public Integer getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(Integer menuNo) {
		this.menuNo = menuNo;
	}
	public Integer getMenuID() {
		return menuID;
	}
	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}
	public Integer getVideoID() {
		return videoID;
	}
	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}
}
