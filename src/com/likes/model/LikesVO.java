package com.likes.model;

import java.io.Serializable;

public class LikesVO implements Serializable {

	private Integer likesID;
	private Integer postsID;
	private Integer userID;


	public Integer getLikesID() {
		return likesID;
	}

	public void setLikesID(Integer likesID) {
		this.likesID = likesID;
	}

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

	@Override
	public String toString() {
		return "LikesVO [likesID=" + likesID + ", postsID=" + postsID + ", userID=" + userID + "]";
	}
	
	
}
