package com.tag.model;

import java.io.Serializable;

public class TagVO implements Serializable {
	
	private Integer tagID;
	private String tagName;
	

	public Integer getTagID() {
		return tagID;
	}

	public void setTagID(Integer tagID) {
		this.tagID = tagID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "TagVO [tagID=" + tagID + ", tagName=" + tagName + "]";
	}
	
}
