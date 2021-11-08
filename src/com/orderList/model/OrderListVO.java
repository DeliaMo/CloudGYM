package com.orderList.model;

import java.io.Serializable;

public class OrderListVO implements Serializable{
	
	private Integer orderListNo;
	private Integer orderNo;
	private Integer itemID;
	
	
	public Integer getOrderListNo() {
		return orderListNo;
	}
	public void setOrderListNo(Integer orderListNo) {
		this.orderListNo = orderListNo;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

}