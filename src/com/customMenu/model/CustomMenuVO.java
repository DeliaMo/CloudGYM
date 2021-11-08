package com.customMenu.model;

import java.sql.Date;
import java.sql.Timestamp;

public class CustomMenuVO {
	private Integer menuID;
	private Integer userID;
	private String content;
	private String title;
	private Timestamp buildTime;
	private Integer completed;
	
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getMenuID() {
		return menuID;
	}
	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp timestamp) {
		this.buildTime = timestamp;
	}
	
	
}
