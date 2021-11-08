package com.collection.model;

import java.io.Serializable;

public class CollectionVO implements Serializable {

	private Integer collectionNo;
	private Integer userID;
	private Integer itemID;

	public CollectionVO() {
		super();
	}

	public CollectionVO(Integer collectionNo, Integer userID, Integer itemID) {
		super();
		this.collectionNo = collectionNo;
		this.userID = userID;
		this.itemID = itemID;
	}

	public Integer getCollectionNo() {
		return collectionNo;
	}

	public void setCollectionNo(Integer collectionNo) {
		this.collectionNo = collectionNo;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getItemID() {
		return itemID;
	}

	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

}
