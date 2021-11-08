package com.comment.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommentVO implements Serializable {

	private Integer commentID;
	private Integer postsID;
	private Integer userID;
	private String commentContent;
	private Timestamp commentPublishDate;
	private boolean commentShow;
	private Integer commentReportedTimes;

	public Integer getCommentID() {
		return commentID;
	}

	public void setCommentID(Integer commentID) {
		this.commentID = commentID;
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

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Timestamp getCommentPublishDate() {
		return commentPublishDate;
	}

	public void setCommentPublishDate(Timestamp commentPublishDate) {
		this.commentPublishDate = commentPublishDate;
	}

	public boolean isCommentShow() {
		return commentShow;
	}

	public void setCommentShow(boolean commentShow) {
		this.commentShow = commentShow;
	}

	public Integer getCommentReportedTimes() {
		return commentReportedTimes;
	}

	public void setCommentReportedTimes(Integer commentReportedTimes) {
		this.commentReportedTimes = commentReportedTimes;
	}

	@Override
	public String toString() {
		return "CommentVO [commentID=" + commentID + ", postsID=" + postsID + ", userID=" + userID + ", commentContent="
				+ commentContent + ", commentPublishDate=" + commentPublishDate + ", commentShow=" + commentShow
				+ ", commentReportedTimes=" + commentReportedTimes + "]";
	}

}
