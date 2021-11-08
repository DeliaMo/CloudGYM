package com.reportRecord.model;

import java.io.Serializable;

public class ReportRecordVO implements Serializable {
	
	private Integer recordID;
	private Integer itemID;
	private Integer userID;
	
	public Integer getRecordID() {
		return recordID;
	}
	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	

}
