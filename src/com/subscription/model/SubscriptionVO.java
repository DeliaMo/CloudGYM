package com.subscription.model;

import java.io.Serializable;

public class SubscriptionVO implements Serializable{
	private Integer subNo;
	private Integer subID;
	private Integer userID;
	
	public Integer getSubNo() {
		return subNo;
	}
	public void setSubNo(Integer subNo) {
		this.subNo = subNo;
	}
	
	public Integer getSubID() {
		return subID;
	}
	public void setSubID(Integer subID) {
		this.subID = subID;
	}
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
