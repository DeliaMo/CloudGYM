package com.process.model;

import java.io.Serializable;

public class ProcessVO implements Serializable{
	
	private Integer processNo;
	private Integer userID;
	private Integer listID;
	private String action;
	private Integer sets;
	private Integer actNo;
	
	public Integer getProcessNo() {
		return processNo;
	}
	public void setProcessNo(Integer processNo) {
		this.processNo = processNo;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getListID() {
		return listID;
	}
	public void setListID(Integer listID) {
		this.listID = listID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getSets() {
		return sets;
	}
	public void setSets(Integer sets) {
		this.sets = sets;
	}
}
