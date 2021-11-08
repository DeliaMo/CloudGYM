package com.coachMenu.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CoachMenuVO implements Serializable {
	
	private Integer menuID;
	private Integer userID;
	private String menuName;
	private Timestamp publishDate;
	private Integer price;
	private Boolean isPublic;
	private Integer positionNo;
	
	
	
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
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Integer getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(Integer positionNo) {
		this.positionNo = positionNo;
	}
}
