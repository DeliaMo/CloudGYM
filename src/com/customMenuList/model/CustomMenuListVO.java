package com.customMenuList.model;

import java.io.Serializable;

public class CustomMenuListVO implements Serializable{
	private Integer videoID;
	private Integer menuID;
	private Integer listID;
	
	public CustomMenuListVO() {
		
	}
	
	public CustomMenuListVO(Integer videoID, Integer menuID, Integer listID) {
		super();
		this.videoID = videoID;
		this.menuID = menuID;
		this.listID = listID;
	}
	
	public Integer getVideoID() {
		return videoID;
	}
	
	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}
	
	public Integer getMenuID() {
		return menuID;
	}
	
	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}
	
	public Integer getListID() {
		return listID;
	}
	
	public void setListID(Integer listID) {
		this.listID = listID;
	}
	
	
	
	
	
	
}
