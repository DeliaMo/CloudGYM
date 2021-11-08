package com.orders.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.sql.DataSource;

import com.orderList.model.*;

import javax.naming.*;

public class OrdersJDBCDAO implements OrdersDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
//		private static DataSource ds = null;
//		static {
//			try {
//				Context ctx = new InitialContext();
//				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}
//		}

	private static final String INSERT_STMT = "INSERT INTO orders(userID, totalPrice) VALUES(?, ?)";
	private static final String UPDATE_STMT = "UPDATE orders SET userID=?, builtDate=?, totalPrice=? WHERE orderNo=?";
	private static final String FIND_BY_ORDERNO_STMT = "SELECT * FROM orders WHERE orderNo = ?";
	private static final String FIND_BY_USERID_STMT = "SELECT * FROM orders WHERE userID = ?";
	private static final String FIND_BY_DATE_STMT = "SELECT * FROM orders WHERE builtDate LIKE \"%\"" + "?" + "\"%\"";
	private static final String FIND_ALL = "SELECT * FROM orders";

	@Override
	public Integer insert(OrdersVO ordersVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer primaryKey = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, 1);

//			pstmt.setInt(1, ordersVO.getOrderNo());
			pstmt.setInt(1, ordersVO.getUserID());
//			pstmt.setTimestamp(3, ordersVO.getBuiltDate());
			pstmt.setInt(2, ordersVO.getTotalPrice());

			pstmt.executeUpdate();

			// 取得自增主鍵
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);
			}
			return primaryKey;

		} catch (Exception se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public Integer insert2(OrdersVO ordersVO, List<Integer> items) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			String[] cols = { "orderNo" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, ordersVO.getUserID());
			pstmt.setInt(2, ordersVO.getTotalPrice());
			pstmt.executeUpdate();

			String next_orderNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_orderNo = rs.getString(1);
			}
			
			rs.close();
			
			
//			OrderListJDBCDAO dao = new OrderListJDBCDAO();
//			for (OrderListVO orderListVO : list) {
//				orderListVO.setOrderNo(Integer.parseInt(next_orderNo));
//				dao.insert2(orderListVO, con);
//			}
			
			OrderListService orderlistSvc = new OrderListService();
			for(Integer itemID : items) {
				orderlistSvc.addOrderList2(Integer.parseInt(next_orderNo), itemID, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			return Integer.parseInt(next_orderNo);
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orders");
					se.printStackTrace();
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(OrdersVO ordersVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, ordersVO.getUserID());
			pstmt.setTimestamp(2, ordersVO.getBuiltDate());
			pstmt.setInt(3, ordersVO.getTotalPrice());
			pstmt.setInt(4, ordersVO.getOrderNo());

			pstmt.executeUpdate();

		} catch (Exception se) {
			se.printStackTrace();
		} finally {
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
	}

	@Override
	public OrdersVO findByOrderNo(Integer orderNo) {
		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ORDERNO_STMT);

			pstmt.setInt(1, orderNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrderNo(rs.getInt("orderNo"));
				ordersVO.setUserID(rs.getInt("userID"));
				ordersVO.setBuiltDate(rs.getTimestamp("builtDate"));
				ordersVO.setTotalPrice(rs.getInt("totalPrice"));
			}
		} catch (Exception se) {
			// TODO Auto-generated catch block
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
		return ordersVO;
	}

	@Override
	public List<OrdersVO> findByUserID(Integer userID) {
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_USERID_STMT);

			pstmt.setInt(1, userID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrderNo(rs.getInt("orderNo"));
				ordersVO.setUserID(rs.getInt("userID"));
				ordersVO.setBuiltDate(rs.getTimestamp("builtDate"));
				ordersVO.setTotalPrice(rs.getInt("totalPrice"));
				list.add(ordersVO);
			}
		} catch (Exception se) {
			// TODO Auto-generated catch block
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
	public List<OrdersVO> findByDate(String date) {

		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_DATE_STMT);

			pstmt.setString(1, date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrderNo(rs.getInt("orderNo"));
				ordersVO.setUserID(rs.getInt("userID"));
				ordersVO.setBuiltDate(rs.getTimestamp("builtDate"));
				ordersVO.setTotalPrice(rs.getInt("totalPrice"));
				list.add(ordersVO);
			}

		} catch (Exception se) {
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

	@Override
	public List<OrdersVO> findAll() {

		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrderNo(rs.getInt("orderNo"));
				ordersVO.setUserID(rs.getInt("userID"));
				ordersVO.setBuiltDate(rs.getTimestamp("builtDate"));
				ordersVO.setTotalPrice(rs.getInt("totalPrice"));
				list.add(ordersVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
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

		OrdersJDBCDAO dao = new OrdersJDBCDAO();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sd.format(new Date(System.currentTimeMillis()));
		Timestamp ts = Timestamp.valueOf(str);

//		Scanner sc = new Scanner(System.in);
//		int orderNo = sc.nextInt();
//		int userID = sc.nextInt();
//		int totalPrice = sc.nextInt();

		// 新增
		OrdersVO ordersVO1 = new OrdersVO();
		ordersVO1.setUserID(1002);
		ordersVO1.setBuiltDate(ts);
		ordersVO1.setTotalPrice(500);

		dao.insert(ordersVO1);

		// 修改
//		OrdersVO ordersVO2 = new OrdersVO();
//		ordersVO2.setOrderNo(orderNo);
//		ordersVO2.setUserID(userID);
//		ordersVO2.setBuiltDate(ts);
//		ordersVO2.setTotalPrice(totalPrice);
//		dao.update(ordersVO2);
//		
		// 用訂單編號查詢
//		OrdersVO ordersVO3 = dao.findByOrderNo(90001);
//		System.out.print(ordersVO3.getOrderNo() + ",");
//		System.out.print(ordersVO3.getUserID() + ",");
//		System.out.print(ordersVO3.getBuiltDate() + ",");
//		System.out.print(ordersVO3.getTotalPrice());
//		System.out.println();

		// 用會員編號查詢
//		OrdersVO ordersVO3 = dao.findByUserID(userID);
//		System.out.print(ordersVO3.getOrderNo() + ",");
//		System.out.print(ordersVO3.getUserID() + ",");
//		System.out.print(ordersVO3.getBuiltDate() + ",");
//		System.out.print(ordersVO3.getTotalPrice());
//		System.out.println();

		// 用日期查詢
//		List<OrdersVO> list = dao.findByDate("1'OR'1'='1");
//		for(OrdersVO orders : list) {
//			System.out.print(orders.getOrderNo() + ",");
//			System.out.print(orders.getUserID() + ",");
//			System.out.print(orders.getBuiltDate() + ",");
//			System.out.print(orders.getTotalPrice());
//			System.out.println();
//		}

		// 查詢全部
//		List<OrdersVO> list2 = dao.findAll();
//		for(OrdersVO orders : list2) {
//			System.out.print(orders.getOrderNo() + ",");
//			System.out.print(orders.getUserID() + ",");
//			System.out.print(orders.getBuiltDate() + ",");
//			System.out.print(orders.getTotalPrice());
//			System.out.println();
//		}

//		System.out.println(ds);

	}

}