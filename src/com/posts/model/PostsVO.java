package com.posts.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class PostsVO implements Serializable {

	private Integer postsID;
	private Integer userID;
	private String postsTitle;
	private String postsContent;
	private byte[] postsImg;
	private Timestamp postsPublishDate;
	private Integer tagID;
	private Integer postsLikes;
	private Integer postsReportedTimes;
	private boolean postsShow;


	public Integer getPostsID() {
		return postsID;
	}

	public void setPostsID(Integer postsID) {
		this.postsID = postsID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getPostsTitle() {
		return postsTitle;
	}

	public void setPostsTitle(String postsTitle) {
		this.postsTitle = postsTitle;
	}

	public String getPostsContent() {
		return postsContent;
	}

	public void setPostsContent(String postsContent) {
		this.postsContent = postsContent;
	}

	public byte[] getPostsImg() {
		return postsImg;
	}

	public void setPostsImg(byte[] postsImg) {
		this.postsImg = postsImg;
	}

	public Timestamp getPostsPublishDate() {
		return postsPublishDate;
	}

	public void setPostsPublishDate(Timestamp postsPublishDate) {
		this.postsPublishDate = postsPublishDate;
	}

	public Integer getTagID() {
		return tagID;
	}

	public void setTagID(Integer tagID) {
		this.tagID = tagID;
	}

	public Integer getPostsLikes() {
		return postsLikes;
	}

	public void setPostsLikes(Integer postsLikes) {
		this.postsLikes = postsLikes;
	}

	public Integer getPostsReportedTimes() {
		return postsReportedTimes;
	}

	public void setPostsReportedTimes(Integer postsReportedTimes) {
		this.postsReportedTimes = postsReportedTimes;
	}

	public boolean isPostsShow() {
		return postsShow;
	}

	public void setPostsShow(boolean postsShow) {
		this.postsShow = postsShow;
	}

	@Override
	public String toString() {
		return "PostsVO [postsID=" + postsID + ", userID=" + userID + ", postsTitle=" + postsTitle + ", postsContent="
				+ postsContent + ", postsImg=" + Arrays.toString(postsImg) + ", postsPublishDate=" + postsPublishDate
				+ ", tagID=" + tagID + ", postsLikes=" + postsLikes + ", postsReportedTimes=" + postsReportedTimes
				+ ", postsShow=" + postsShow + "]";
	}
	
}