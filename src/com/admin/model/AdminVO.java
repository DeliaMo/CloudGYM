package com.admin.model;

import java.io.Serializable;

public class AdminVO implements Serializable{
	private Integer adminID;
	private String adminName;
	private String adminPW;
	private Integer commentAuth;
	private Integer videoAuth;
	private Integer subAuth;
	private Integer userAuth;
	
	
	public Integer getAdminID() {
		return adminID;
	}
	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}
	
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	public Integer getCommentAuth() {
		return commentAuth;
	}
	public void setCommentAuth(Integer commentAuth) {
		this.commentAuth = commentAuth;
	}
	public Integer getVideoAuth() {
		return videoAuth;
	}
	public void setVideoAuth(Integer videoAuth) {
		this.videoAuth = videoAuth;
	}
	public Integer getSubAuth() {
		return subAuth;
	}
	public String getAdminPW() {
		return adminPW;
	}
	public void setAdminPW(String adminPW) {
		this.adminPW = adminPW;
	}
	public void setSubAuth(Integer subAuth) {
		this.subAuth = subAuth;
	}
	public Integer getUserAuth() {
		return userAuth;
	}
	public void setUserAuth(Integer userAuth) {
		this.userAuth = userAuth;
	}
	
	

}
