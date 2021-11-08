package com.reportRecord.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.report.model.ReportJDBCDAO;
import com.report.model.ReportVO;

public class ReportRecordJDBCDAO implements ReportRecordDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWORD = "123456";
	              
	private static final String INSERT_STMT = "INSERT INTO reportRecord (userID,itemID) VALUES (?, ?)";
	private static final String DELETE_STMT = "DELETE FROM reportRecord where itemID = ?";
	private static final String FIND_BY_USERID_STMT = "SELECT * FROM reportRecord WHERE userID = ?";	
	private static final String FIND_ALL = "SELECT * FROM reportRecord"; 
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void insert(ReportRecordVO reportRecordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, reportRecordVO.getUserID());
			pstmt.setInt(2, reportRecordVO.getItemID());

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
	public void delete(Integer itemID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, itemID);
			
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
	public List<ReportRecordVO> findbyuserID(Integer userID) {
		List<ReportRecordVO> UserList = new ArrayList<ReportRecordVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportRecordVO ReportRecordVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_USERID_STMT);
			
			pstmt.setInt(1, userID);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportRecordVO = new ReportRecordVO();
				ReportRecordVO.setRecordID(rs.getInt("recordID"));
				ReportRecordVO.setItemID(rs.getInt("itemID"));
				ReportRecordVO.setUserID(rs.getInt("userID"));
				UserList.add(ReportRecordVO);
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
	public List<ReportRecordVO> findAll() {
		List<ReportRecordVO> allList = new ArrayList<ReportRecordVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportRecordVO ReportRecordVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReportRecordVO = new ReportRecordVO();
				ReportRecordVO.setRecordID(rs.getInt("recordID"));
				ReportRecordVO.setItemID(rs.getInt("itemID"));
				ReportRecordVO.setUserID(rs.getInt("userID"));
				allList.add(ReportRecordVO);
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
		ReportRecordJDBCDAO dao = new ReportRecordJDBCDAO();
		
		//新增
//		ReportRecordVO rpt1 = new ReportRecordVO();
//		rpt1.setUserID(1004);
//		rpt1.setItemID(40003);
//		dao.insert(rpt1);
		
		//刪除
		dao.delete(50019);
		
		//userID查詢
//		List<ReportRecordVO> reportRecordVO = dao.findbyuserID(1003);
//		for (ReportRecordVO Rvo : reportRecordVO) {			
//			System.out.print(Rvo.getRecordID()+",");
//			System.out.print(Rvo.getItemID()+",");
//			System.out.println(Rvo.getUserID());
//		}
		
		
		//查全部資料
		List<ReportRecordVO> reportRecordVO1 = dao.findAll();
		for (ReportRecordVO Rvo : reportRecordVO1) {			
			System.out.print(Rvo.getRecordID()+",");
			System.out.print(Rvo.getItemID()+",");
			System.out.println(Rvo.getUserID());
		}

	}

}
