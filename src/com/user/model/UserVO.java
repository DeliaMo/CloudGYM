package com.user.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


public class UserVO implements Serializable {

	private Integer userID;
	private String userAccount;
	private String userName;
	private String userPassword;
	private String userMobile;
	private String userSex;
	private Date userBirthday;
	private Timestamp userRegisterDate;
	private Integer userReportedTimes;

	public UserVO() {
		super();
	}

	public UserVO(Integer userID, String userAccount, String userName, String userPassword, String userMobile,
			String userSex, Date userBirthday, Timestamp userRegisterDate, Integer userReportedTimes) {
		super();
		this.userID = userID;
		this.userAccount = userAccount;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userSex = userSex;
		this.userBirthday = userBirthday;
		this.userRegisterDate = userRegisterDate;
		this.userReportedTimes = userReportedTimes;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public Timestamp getUserRegisterDate() {
		return userRegisterDate;
	}

	public void setUserRegisterDate(Timestamp userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}

	public Integer getUserReportedTimes() {
		return userReportedTimes;
	}

	public void setUserReportedTimes(Integer userReportedTimes) {
		this.userReportedTimes = userReportedTimes;
	}

}
