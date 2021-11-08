package com.admin.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminJDBCDAO implements AdminDAO_interface {
	
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWORD = "123456";

	private static final String UPDATE_STMT = "UPDATE admin SET adminName = ?,adminPW = ?,commentAuth = ?, videoAuth = ?, subAuth = ?, userAuth = ? WHERE adminID = ?";
	private static final String FIND_BY_ADMINID_STMT = "SELECT * FROM admin WHERE adminID = ?";
	private static final String FIND_ALL = "SELECT * FROM admin"; 
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void update(AdminVO adminVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, adminVO.getAdminName());
			pstmt.setString(2, adminVO.getAdminPW());
			pstmt.setInt(3, adminVO.getCommentAuth());
			pstmt.setInt(4, adminVO.getVideoAuth());
			pstmt.setInt(5, adminVO.getSubAuth());
			pstmt.setInt(6, adminVO.getUserAuth());
			pstmt.setInt(7, adminVO.getAdminID());
			
			int rowCount = pstmt.executeUpdate();
			System.out.println("修改" + rowCount + "筆資料");
			
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally{
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
	public AdminVO findbyAdminID(Integer adminID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminVO adminVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ADMINID_STMT);
			
			pstmt.setInt(1, adminID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdminID(rs.getInt("adminID"));
				adminVO.setAdminName(rs.getString("adminName"));
				adminVO.setAdminPW(rs.getString("adminPW"));
				adminVO.setCommentAuth(rs.getInt("commentAuth"));
				adminVO.setVideoAuth(rs.getInt("videoAuth"));
				adminVO.setSubAuth(rs.getInt("subAuth"));
				adminVO.setUserAuth(rs.getInt("userAuth"));
				
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
		return adminVO;
	}

	@Override
	public List<AdminVO> findAll() {
		List<AdminVO> adminList = new ArrayList<AdminVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminVO adminVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdminID(rs.getInt("adminID"));
				adminVO.setAdminName(rs.getString("adminName"));
				adminVO.setAdminPW(rs.getString("adminPW"));
				adminVO.setCommentAuth(rs.getInt("commentAuth"));
				adminVO.setVideoAuth(rs.getInt("videoAuth"));
				adminVO.setSubAuth(rs.getInt("subAuth"));
				adminVO.setUserAuth(rs.getInt("userAuth"));
				adminList.add(adminVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		
		return adminList;
	}

	public static void main(String[] args) {
		AdminJDBCDAO dao =new AdminJDBCDAO();
		
		//修改全部資料
//		AdminVO adminVO = new AdminVO();
//		adminVO.setAdminID(9003);
//		adminVO.setAdminName("Eva");
//		adminVO.setAdminPW("777");
//		adminVO.setCommentAuth(1);
//		adminVO.setVideoAuth(0);
//		adminVO.setSubAuth(1);
//		adminVO.setUserAuth(0);
//		dao.update(adminVO);
		
		//修改單筆資料
		AdminVO adminVO1 = dao.findbyAdminID(9003);
//		adminVO1.setAdminID(9003);
//		adminVO1.setAdminName("Eva");
//		adminVO1.setAdminPW("797");
//		adminVO1.setCommentAuth(1);
//		adminVO1.setVideoAuth(0);
		adminVO1.setSubAuth(0);
//		adminVO1.setUserAuth(0);
		dao.update(adminVO1);
		
		//單筆查詢
		AdminVO adminVO2 = dao.findbyAdminID(9003);
		System.out.print(adminVO2.getAdminID()+",");
		System.out.print(adminVO2.getAdminName()+",");
		System.out.print(adminVO2.getAdminPW()+",");		
		System.out.print(adminVO2.getCommentAuth()+",");
		System.out.print(adminVO2.getVideoAuth()+",");
		System.out.print(adminVO2.getSubAuth()+",");
		System.out.println(adminVO2.getUserAuth());
		System.out.println("=================");
		
		//全部查詢
		List<AdminVO> list = dao.findAll();
		for (AdminVO avo : list) {
			System.out.print(avo.getAdminID()+",");
			System.out.print(avo.getAdminName()+",");
			System.out.print(avo.getAdminPW()+",");
			System.out.print(avo.getCommentAuth()+",");
			System.out.print(avo.getVideoAuth()+",");
			System.out.print(avo.getSubAuth()+",");
			System.out.print(avo.getUserAuth());
			System.out.println();
		}
	}

}

