package com.coach.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class CoachVO implements Serializable{
	
	private Integer userID;
	private String coachAccount;
	private String coachName;
	private String coachPassword;
	private byte[] coachImg;
	private String userMobile;
	private String coachSex;
	private Date coachBirthday;
	private String coachDescription;
	private Date coachRegisteredDate;
	private String coachCertificate;
	private Integer reportedTimes;
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public String getCoachAccount() {
		return coachAccount;
	}
	public void setCoachAccount(String coachAccount) {
		this.coachAccount = coachAccount;
	}
	
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	
	public String getCoachPassword() {
		return coachPassword;
	}
	public void setCoachPassword(String coachPassword) {
		this.coachPassword = coachPassword;
	}
	
	public byte[] getCoachImg() {
		return coachImg;
	}
	
	public void setCoachImg(byte[] coachImg) {
		this.coachImg = coachImg;
	}
	
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getCoachSex() {
		return coachSex;
	}
	public void setCoachSex(String coachSex) {
		this.coachSex = coachSex;
	}
	
	public Date getCoachBirthday() {
		return coachBirthday;
	}
	public void setCoachBirthday(Date coachBirthday) {
		this.coachBirthday = coachBirthday;
	}
	
	public String getCoachDescription() {
		return coachDescription;
	}
	public void setCoachDescription(String coachDescription) {
		this.coachDescription = coachDescription;
	}
	
	public Date getCoachRegisteredDate() {
		return coachRegisteredDate;
	}
	public void setCoachRegisteredDate(Date coachRegisteredDate) {
		this.coachRegisteredDate = coachRegisteredDate;
	}
	
	public String getCoachCertificate() {
		return coachCertificate;
	}
	public void setCoachCertificate(String coachCertificate) {
		this.coachCertificate = coachCertificate;
	}
	
	public Integer getReportedTimes() {
		return reportedTimes;
	}
	public void setReportedTimes(Integer reportedTimes) {
		this.reportedTimes = reportedTimes;
	}
}