package com.orders.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersService {

	private OrdersDAO_interface dao;

	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String str = sd.format(new Date(System.currentTimeMillis()));
	Timestamp ts = Timestamp.valueOf(str);

	public OrdersService() {
		dao = new OrdersJDBCDAO();
	}

	public OrdersVO addOrders(Integer userID, Integer totalPrice) {
		OrdersVO ordersVO = new OrdersVO();

		ordersVO.setUserID(userID);
		ordersVO.setTotalPrice(totalPrice);
		Integer orderNo = dao.insert(ordersVO);
		ordersVO.setOrderNo(orderNo);
		return ordersVO;
	}

	public OrdersVO addOrders2(Integer userID, Integer totalPrice, List<Integer> items) {
		OrdersVO ordersVO = new OrdersVO();

		ordersVO.setUserID(userID);
		ordersVO.setTotalPrice(totalPrice);
		Integer orderNo = dao.insert2(ordersVO, items);
		ordersVO.setOrderNo(orderNo);
		return ordersVO;
	}

	public OrdersVO updateOrders(Integer orderNo, Integer userID, Integer totalPrice) {
		OrdersVO ordersVO = new OrdersVO();

		ordersVO.setOrderNo(orderNo);
		ordersVO.setUserID(userID);
		ordersVO.setBuiltDate(ts);
		ordersVO.setTotalPrice(totalPrice);

		dao.update(ordersVO);
		return ordersVO;
	}

	public OrdersVO gerOrdersByOrderNo(Integer orderNo) {
		return dao.findByOrderNo(orderNo);
	}

	public List<OrdersVO> getOrdersByUserID(Integer userID) {
		return dao.findByUserID(userID);
	}

	public List<OrdersVO> getOrdersByDate(String date) {
		return dao.findByDate(date);
	}

	public List<OrdersVO> getAll() {
		return dao.findAll();
	}
}
