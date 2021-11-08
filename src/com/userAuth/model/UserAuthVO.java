package com.userAuth.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserAuthVO implements Serializable {
	
	private Integer userAuthID;
	private Integer userID;
	private Integer banComment;
	private Integer banShopping;
	private Integer banVideo;
	private Integer banUsers;
	private Timestamp startTime;
	
	public Integer getUserAuthID() {
		return userAuthID;
	}
	public void setUserAuthID(Integer userAuthID) {
		this.userAuthID = userAuthID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getBanComment() {
		return banComment;
	}
	public void setBanComment(Integer banComment) {
		this.banComment = banComment;
	}
	public Integer getBanShopping() {
		return banShopping;
	}
	public void setBanShopping(Integer banShopping) {
		this.banShopping = banShopping;
	}
	public Integer getBanVideo() {
		return banVideo;
	}
	public void setBanVideo(Integer banVideo) {
		this.banVideo = banVideo;
	}
	public Integer getBanUsers() {
		return banUsers;
	}
	public void setBanUsers(Integer banUsers) {
		this.banUsers = banUsers;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	

}
