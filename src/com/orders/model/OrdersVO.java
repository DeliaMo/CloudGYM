package com.orders.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class OrdersVO implements Serializable {
	private Integer orderNo;
	private Integer userID;
	private Timestamp builtDate;
	private Integer totalPrice;
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Timestamp getBuiltDate() {
		return builtDate;
	}
	public void setBuiltDate(Timestamp builtDate) {
		this.builtDate = builtDate;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
}