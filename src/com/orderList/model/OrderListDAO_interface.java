package com.orderList.model;

import java.sql.Connection;
import java.util.List;

public interface OrderListDAO_interface {
	public void insert(OrderListVO orderListVO);
	public void update(OrderListVO orderListVO);
	public void delete(Integer orderListNo);
	public OrderListVO findByOrderListNo(Integer orderListNo);
	public List<OrderListVO> findByOrderNo(Integer orderNo);
	public List<OrderListVO> findAll();
	
	public void insert2(OrderListVO orderListVO, Connection con);
}