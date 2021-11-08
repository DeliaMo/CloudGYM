package com.video.model;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class VideoVO implements Serializable{
	private Integer videoID;
	private Integer userID;
	private String title;
	private Integer duration;
	private Integer price;
	private String intro;
	private Timestamp publishTime;
	private Integer review;
	private Integer reportedTimes;
	private Boolean listed;
	private String level;
	private byte[] img;
	private byte[] content;
	private Integer thePosition;
	
	

	public VideoVO() {
		
	}

	public Integer getVideoID() {
		return videoID;
	}

	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getReview() {
		return review;
	}

	public void setReview(Integer review) {
		this.review = review;
	}

	public Integer getReportedTimes() {
		return reportedTimes;
	}

	public void setReportedTimes(Integer reportedTimes) {
		this.reportedTimes = reportedTimes;
	}

	public Boolean getListed() {
		return listed;
	}

	public void setListed(Boolean listed) {
		this.listed = listed;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content2) {
		this.content = content2;
	}

	public Integer getThePosition() {
		return thePosition;
	}

	public void setThePosition(Integer thePosition) {
		this.thePosition = thePosition;
	}
}
