package com.orderList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class OrderListJDBCDAO implements OrderListDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO orderList(orderNo, itemID) VALUES(?, ?)";
	private static final String UPDATE_STMT = "UPDATE orderList SET orderNo=?, itemID=? WHERE orderListNo=?";
	private static final String DELETE_STMT = "DELETE FROM orderList WHERE orderListNo=?";
	private static final String FIND_BY_ORDERLISTNO = "SELECT * FROM orderList WHERE orderListNo=?";
	private static final String FIND_BY_ORDERNO = "SELECT * FROM orderList WHERE orderNo=?";
	private static final String FIND_ALL = "SELECT * FROM orderList";

	@Override
	public void insert(OrderListVO orderListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, orderListVO.getOrderNo());
			pstmt.setInt(2, orderListVO.getItemID());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void insert2(OrderListVO orderListVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, orderListVO.getOrderNo());
			pstmt.setInt(2, orderListVO.getItemID());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(OrderListVO orderListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, orderListVO.getOrderNo());
			pstmt.setInt(2, orderListVO.getItemID());
			pstmt.setInt(3, orderListVO.getOrderListNo());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Integer orderListNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, orderListNo);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public OrderListVO findByOrderListNo(Integer orderListNo) {

		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ORDERLISTNO);

			pstmt.setInt(1, orderListNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderListVO = new OrderListVO();
				orderListVO.setOrderListNo(rs.getInt("orderListNo"));
				orderListVO.setOrderNo(rs.getInt("orderNo"));
				orderListVO.setItemID(rs.getInt("itemID"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return orderListVO;
	}

	@Override
	public List<OrderListVO> findByOrderNo(Integer orderNo) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ORDERNO);

			pstmt.setInt(1, orderNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderListVO = new OrderListVO();
				orderListVO.setOrderListNo(rs.getInt("orderListNo"));
				orderListVO.setOrderNo(rs.getInt("orderNo"));
				orderListVO.setItemID(rs.getInt("itemID"));
				list.add(orderListVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<OrderListVO> findAll() {

		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderListVO = new OrderListVO();
				orderListVO.setOrderListNo(rs.getInt("orderListNo"));
				orderListVO.setOrderNo(rs.getInt("orderNo"));
				orderListVO.setItemID(rs.getInt("itemID"));
				list.add(orderListVO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		OrderListJDBCDAO dao = new OrderListJDBCDAO();
//		Scanner sc = new Scanner(System.in);
//		int orderListNo = sc.nextInt();
//		int orderNo = sc.nextInt();
//		int itemID = sc.nextInt();

		// 新增
		OrderListVO olvo1 = new OrderListVO();
		olvo1.setOrderNo(90006);
		olvo1.setItemID(30009);
		dao.insert(olvo1);

		// 修改
//		OrderListVO olvo2 = new OrderListVO();
//		olvo2.setOrderListNo(orderListNo);
//		olvo2.setOrderNo(orderNo);
//		olvo2.setItemID(itemID);
//		dao.update(olvo2);
		
		// 刪除
//		dao.delete(21);
		
		// 用訂單明細編號查詢
//		OrderListVO olvo3 = dao.findByOrderListNo(20);
//		System.out.print(olvo3.getOrderListNo() + ", ");
//		System.out.print(olvo3.getOrderNo() + ", ");
//		System.out.print(olvo3.getItemID());
//		System.out.println();
//		System.out.println("---------------------------");
//		
		// 用訂單編號查詢
		List<OrderListVO> list = dao.findByOrderNo(90003);
		for(OrderListVO orderListVO : list) {
			System.out.print(orderListVO.getOrderListNo() + ", ");
			System.out.print(orderListVO.getOrderNo() + ", ");
			System.out.print(orderListVO.getItemID());
			System.out.println();
		}

		// 查詢全部
//		List<OrderListVO> list2 = dao.findAll();
//		for (OrderListVO orderListVO : list2) {
//			System.out.print(orderListVO.getOrderListNo() + ", ");
//			System.out.print(orderListVO.getOrderNo() + ", ");
//			System.out.print(orderListVO.getItemID());
//			System.out.println();
//		}
	}

}
