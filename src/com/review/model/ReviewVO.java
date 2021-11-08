package com.review.model;

import java.io.Serializable;

public class ReviewVO implements Serializable{
	private Integer reviewNo;
	private Integer videoID;
	private Integer star;
	
	public ReviewVO() {
		
	}
	
	public ReviewVO(Integer reviewNo, Integer videoID, Integer star) {
		super();
		this.reviewNo = reviewNo;
		this.videoID = videoID;
		this.star = star;
	}

	public Integer getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(Integer reviewNo) {
		this.reviewNo = reviewNo;
	}

	public Integer getVideoID() {
		return videoID;
	}

	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	
}
