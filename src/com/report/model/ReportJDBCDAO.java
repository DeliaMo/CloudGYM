package com.report.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportJDBCDAO implements ReportDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWORD = "123456";
	              
	private static final String INSERT_STMT = "INSERT INTO report (userID,itemID,reportTime) VALUES (?, ?, ?)";
	private static final String DELETE_STMT = "DELETE FROM report where userID = ? && itemID = ?";
	private static final String FIND_BY_USERID_STMT = "SELECT * FROM report WHERE userID = ?";
	private static final String FIND_BY_ITTMID_STMT = "SELECT * FROM report WHERE itemID = ?";	
	private static final String FIND_ALL = "SELECT * FROM report"; 
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(ReportVO reportVO) {	
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, reportVO.getUserID());
			pstmt.setInt(2, reportVO.getItemID());
			pstmt.setTimestamp(3, reportVO.getReportTime());

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
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
	public void delete(ReportVO reportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, reportVO.getUserID());
			pstmt.setInt(2, reportVO.getItemID());
			
			pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
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
	public List<ReportVO> findbyUserID(Integer userID) {
		List<ReportVO> UserList = new ArrayList<ReportVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportVO ReportVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_USERID_STMT);
			
			pstmt.setInt(1, userID);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportVO = new ReportVO();
				ReportVO.setReportID(rs.getInt("reportID"));
				ReportVO.setUserID(rs.getInt("userID"));
				ReportVO.setItemID(rs.getInt("itemID"));
				ReportVO.setReportTime(rs.getTimestamp("reportTime"));
				UserList.add(ReportVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
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
		
		return UserList;
	}

	@Override
	public List<ReportVO> findbyItemID(Integer itemID) {
		List<ReportVO> ItemList = new ArrayList<ReportVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportVO ReportVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ITTMID_STMT);
			
			pstmt.setInt(1, itemID);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportVO = new ReportVO();
				ReportVO.setReportID(rs.getInt("reportID"));
				ReportVO.setUserID(rs.getInt("userID"));
				ReportVO.setItemID(rs.getInt("itemID"));
				ReportVO.setReportTime(rs.getTimestamp("reportTime"));
				ItemList.add(ReportVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
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
		
		return ItemList;
	}

	@Override
	public List<ReportVO> findAll() {
		List<ReportVO> allList = new ArrayList<ReportVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportVO ReportVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportVO = new ReportVO();
				ReportVO.setReportID(rs.getInt("reportID"));
				ReportVO.setUserID(rs.getInt("userID"));
				ReportVO.setItemID(rs.getInt("itemID"));
				ReportVO.setReportTime(rs.getTimestamp("reportTime"));
				allList.add(ReportVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
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
		
		return allList;
	}

	public static void main(String[] args) {
		ReportJDBCDAO dao = new ReportJDBCDAO();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sd.format(new Date(System.currentTimeMillis()));
		Timestamp ts = Timestamp.valueOf(str);
		
		//新增
//		ReportVO rpt1 = new ReportVO();
//		rpt1.setUserID(1004);
//		rpt1.setItemID(50001);
//		rpt1.setReportTime(ts);
//		dao.insert(rpt1);
		
//		刪除
//		ReportVO rpt2 = new ReportVO();
//		rpt2.setUserID(1005);
//		rpt2.setItemID(50019);
//		dao.delete(rpt2);
		
		//userID查詢
//		List<ReportVO> ReportVO1 = dao.findbyUserID(1001);
//		for (ReportVO Rvo : ReportVO1) {			
//			System.out.print(Rvo.getReportID()+",");
//			System.out.print(Rvo.getUserID()+",");
//			System.out.print(Rvo.getItemID()+",");
//			System.out.println(Rvo.getReportTime());	
//		}
		
		//itemID查詢
//		List<ReportVO> ReportVO2 = dao.findbyItemID(50001);
//		for (ReportVO Rvo : ReportVO2) {			
//			System.out.print(Rvo.getReportID()+",");
//			System.out.print(Rvo.getUserID()+",");
//			System.out.print(Rvo.getItemID()+",");
//			System.out.println(Rvo.getReportTime());	
//		}
		
		//查全部資料
		List<ReportVO> ReportVO3 = dao.findAll();
		for (ReportVO Rvo : ReportVO3) {			
			System.out.print(Rvo.getReportID()+",");
			System.out.print(Rvo.getUserID()+",");
			System.out.print(Rvo.getItemID()+",");
			System.out.println(Rvo.getReportTime());	
		}
		
	}

}
