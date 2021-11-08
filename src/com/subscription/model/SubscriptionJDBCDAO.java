package com.subscription.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubscriptionJDBCDAO  implements SubscriptionDAO_interface{
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/cloudGYM?serverTimezone=Asia/Taipei";
	private static final String userid = "David";
	private static final String passwd = "123456";

	private static final String INSERT_STMT ="INSERT INTO subscription(subID,userID)VALUES(?,?)";
	private static final String UPDATE_STMT ="UPDATE subscription SET subID=?,userID=? WHERE subNo=?";
	private static final String DELETE_STMT = "DELETE FROM subscription WHERE subNo=?";
	private static final String FIND_BY_SUBNO = "SELECT * FROM subscription WHERE subNo=?";
	private static final String FIND_BY_USERID = "SELECT * FROM subscription WHERE userID=?";
	private static final String FIND_ALL = "SELECT * FROM subscription";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	@Override
	public void insert(SubscriptionVO subscriptionVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, subscriptionVO.getSubID());
			pstmt.setInt(2, subscriptionVO.getUserID());
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
		throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
	} catch (SQLException se) {
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
	public void update(SubscriptionVO subscriptionVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			

			pstmt.setInt(1, subscriptionVO.getSubID());
			pstmt.setInt(2, subscriptionVO.getUserID());
			pstmt.setInt(3, subscriptionVO.getSubNo());
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer subNo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, subNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public SubscriptionVO findBySubNo(Integer subNo) {
		// TODO Auto-generated method stub
		SubscriptionVO subscriptionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_SUBNO);
			pstmt.setInt(1, subNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				subscriptionVO = new SubscriptionVO();
				subscriptionVO.setSubNo(rs.getInt("subNo"));
				subscriptionVO.setSubID(rs.getInt("subID"));
				subscriptionVO.setUserID(rs.getInt("userID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return subscriptionVO;
	}
	
	@Override
	public List<SubscriptionVO> findByUserID(Integer userID) {
		List<SubscriptionVO> list = new ArrayList<SubscriptionVO>();
		SubscriptionVO subscriptionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_USERID);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				subscriptionVO = new SubscriptionVO();
				subscriptionVO.setSubNo(rs.getInt("subNo"));
				subscriptionVO.setSubID(rs.getInt("subID"));
				subscriptionVO.setUserID(rs.getInt("userID"));

				list.add(subscriptionVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
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
	public List<SubscriptionVO> findAll() {
		// TODO Auto-generated method stub
		List<SubscriptionVO> list = new ArrayList<SubscriptionVO>();
		SubscriptionVO subscriptionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				subscriptionVO = new SubscriptionVO();
				subscriptionVO.setSubNo(rs.getInt("subNo"));
				subscriptionVO.setSubID(rs.getInt("subID"));
				subscriptionVO.setUserID(rs.getInt("userID"));

				list.add(subscriptionVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
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
		SubscriptionJDBCDAO dao = new SubscriptionJDBCDAO();
		
		
		// 新增
//		SubscriptionVO sub = new SubscriptionVO();
//		sub.setSubID(70001);
//		sub.setUserID(2001);
//		dao.insert(sub);
//		System.out.println("新增成功");
		
		// 修改
//		SubscriptionVO sub2 = new SubscriptionVO();
//		sub2.setSubNo(11);
//		sub2.setSubID(70002);
//		sub2.setUserID(2002);
//		dao.update(sub2);
//		System.out.println("修改成功");
		
		
		// 刪除
//		dao.delete(16);
//		System.out.println("刪除成功");
		
		// 用subNo查詢
//		SubscriptionVO sub = dao.findBySubNo(15);
//		System.out.print(sub.getSubNo() + ", ");
//		System.out.print(sub.getSubID() + ", ");
//		System.out.print(sub.getUserID());
//		System.out.println();
		
		// 用userID查詢
		List<SubscriptionVO> list1 = dao.findByUserID(2001);
		for(SubscriptionVO subVo : list1) {
			System.out.print(subVo.getSubNo() + ", ");
			System.out.print(subVo.getSubID() + ", ");
			System.out.print(subVo.getUserID() );
			System.out.println();
		}
		
		// 查詢全部

				List<SubscriptionVO> list = dao.findAll();
				for(SubscriptionVO subVo : list) {
					System.out.print(subVo.getSubNo() + ", ");
					System.out.print(subVo.getSubID() + ", ");
					System.out.print(subVo.getUserID() );
					System.out.println();
				}
	}
}
