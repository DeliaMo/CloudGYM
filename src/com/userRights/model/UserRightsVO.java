 package com.userRights.model;

import java.io.Serializable;

public class UserRightsVO implements Serializable{
	private Integer rightsID;
	private Integer userID;
	private Integer videoID;
	private Integer duration;
	
	public UserRightsVO() {
		
	}

	public UserRightsVO(Integer rightsID, Integer userID, Integer videoID, Integer duration) {
		super();
		this.rightsID = rightsID;
		this.userID = userID;
		this.videoID = videoID;
		this.duration = duration;
	}

	public Integer getRightsID() {
		return rightsID;
	}

	public void setRightsID(Integer rightsID) {
		this.rightsID = rightsID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getVideoID() {
		return videoID;
	}

	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}
